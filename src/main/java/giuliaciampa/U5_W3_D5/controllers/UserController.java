package giuliaciampa.U5_W3_D5.controllers;

import giuliaciampa.U5_W3_D5.DTO.PrenotazioneDTO;
import giuliaciampa.U5_W3_D5.entities.Prenotazione;
import giuliaciampa.U5_W3_D5.entities.Utente;
import giuliaciampa.U5_W3_D5.services.ReservationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {

    //ATTRIBUTO
    private final ReservationService reservationService;

    public UserController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/prenotazioni")
    public List<PrenotazioneDTO> getReservation(@AuthenticationPrincipal Utente currentAuthenticatedUser) {

        List<Prenotazione> listaPrenotazioni = this.reservationService.getReservation(currentAuthenticatedUser);
        return listaPrenotazioni.stream().map(prenotazione -> new PrenotazioneDTO(prenotazione.getEvento().getTitolo(), prenotazione.getEvento().getData())).toList();

    }
}
