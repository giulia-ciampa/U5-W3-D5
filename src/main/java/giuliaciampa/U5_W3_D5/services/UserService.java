package giuliaciampa.U5_W3_D5.services;

import giuliaciampa.U5_W3_D5.DTO.UserDTO;
import giuliaciampa.U5_W3_D5.entities.Utente;
import giuliaciampa.U5_W3_D5.exceptions.BadRequestException;
import giuliaciampa.U5_W3_D5.exceptions.NotFoundException;
import giuliaciampa.U5_W3_D5.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository usersRepository;
    private final PasswordEncoder bcrypt;

    public UserService(UserRepository usersRepository, PasswordEncoder bcrypt) {
        this.usersRepository = usersRepository;
        this.bcrypt = bcrypt;
    }

    //METODI

    //SALVA L'UTENTE
    public Utente saveUser(UserDTO payload) {
        //controllo che l'email non esista già

        if (this.usersRepository.existsByEmail(payload.email()))
            throw new BadRequestException("l'email " + payload.email() + " esiste già.");

        //creo il nuovo oggetto

        Utente nuovoUtente = new Utente(payload.email(), this.bcrypt.encode(payload.password()), payload.nome(), payload.cognome());

        //salvo l'utente

        Utente utenteSalvato = this.usersRepository.save(nuovoUtente);

        return utenteSalvato;
    }

    //FINDBYEMAIL
    public Utente findByEmail(String email) {
        return this.usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato!"));
    }
}

