package giuliaciampa.U5_W3_D5.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EventoDTO(
        @NotBlank(message = "l'evento deve avere un titolo")
        String titolo,

        String descrizione,

        @NotNull(message = "La data è obbligatoria")
        @FutureOrPresent(message = "la data non può essere passata")
        LocalDate data,

        @NotBlank(message = "il luogo è obbligatorio")
        String luogo,

        @Min(value = 1, message = "L'evento deve prevedere almeno 1 posto")
        int numeroPosti) {
}
