package giuliaciampa.U5_W3_D5.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDTO(
        @NotBlank(message = "il nome deve essere inserito")
        String nome,
        @NotBlank(message = "il cognome deve essere inserito")
        String cognome,
        @Email(message = "inserire il formato dell'email corretto")
        String email,
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "la password deve contenere almeno un numero, una lettera maiuscola e deve essere lunga almeno 8 caratteri")
        String password) {
}
