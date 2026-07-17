package giuliaciampa.U5_W3_D5.controllers;

import giuliaciampa.U5_W3_D5.entities.Prenotazione;
import giuliaciampa.U5_W3_D5.entities.Utente;
import giuliaciampa.U5_W3_D5.services.ReservationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/{idEvento}")
    @PreAuthorize("hasAuthority('UTENTE_NORMALE')")
    public Prenotazione prenota(@PathVariable UUID idEvento, @AuthenticationPrincipal Utente utenteLoggato) {
        return reservationService.prenota(idEvento, utenteLoggato);
    }
}
