package giuliaciampa.U5_W3_D5.services;

import giuliaciampa.U5_W3_D5.DTO.LoginDTO;
import giuliaciampa.U5_W3_D5.entities.Utente;
import giuliaciampa.U5_W3_D5.exceptions.UnauthorizedException;
import giuliaciampa.U5_W3_D5.security.JWTTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService usersService;
    private final PasswordEncoder bcrypt;
    private final JWTTools jwtTools;

    public AuthService(UserService usersService, PasswordEncoder bcrypt, JWTTools jwtTools) {
        this.usersService = usersService;
        this.bcrypt = bcrypt;
        this.jwtTools = jwtTools;
    }

    //LOGIN -> CONTROLLO CREDENZIALI E GENERA TOKEN

    public String checkCredentialsAndGenerateToken(LoginDTO payload) {
        //email
        Utente utenteTrovato = this.usersService.findByEmail(payload.email());

        //password
        if (this.bcrypt.matches(payload.password(), utenteTrovato.getPassword())) {


            return this.jwtTools.generateToken(utenteTrovato);
        } else {
            // 3. Altrimenti --> 401 ("Credenziali Sbagliate")
            throw new UnauthorizedException("Credenziali Sbagliate");
        }
    }

}
