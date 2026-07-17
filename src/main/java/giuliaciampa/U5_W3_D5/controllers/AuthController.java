package giuliaciampa.U5_W3_D5.controllers;

import giuliaciampa.U5_W3_D5.DTO.LoginDTO;
import giuliaciampa.U5_W3_D5.DTO.LoginResponseDTO;
import giuliaciampa.U5_W3_D5.DTO.UserDTO;
import giuliaciampa.U5_W3_D5.DTO.UserResponseDTO;
import giuliaciampa.U5_W3_D5.entities.Utente;
import giuliaciampa.U5_W3_D5.exceptions.ValidationException;
import giuliaciampa.U5_W3_D5.services.AuthService;
import giuliaciampa.U5_W3_D5.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //ATTRIBUTI
    private final UserService userService;
    private final AuthService authService;


    //COSTRUTTORE
    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    //METODI
    //SALVA UTENTE - REGISTRA AL DB
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)

    public UserResponseDTO saveUser(@RequestBody @Validated UserDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errorsList);
        }

        Utente utenteSalvato = this.userService.saveUser(payload);

        return new UserResponseDTO(utenteSalvato.getId());
    }

    //LOGIN
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO credenziali) {
        return new LoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(credenziali));
    }

}
