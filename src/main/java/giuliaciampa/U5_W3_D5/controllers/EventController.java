package giuliaciampa.U5_W3_D5.controllers;

import giuliaciampa.U5_W3_D5.DTO.EventoDTO;
import giuliaciampa.U5_W3_D5.entities.Evento;
import giuliaciampa.U5_W3_D5.entities.Utente;
import giuliaciampa.U5_W3_D5.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/eventi")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    public Evento save(@RequestBody EventoDTO payload, @AuthenticationPrincipal Utente organizzatore) {
        return eventService.save(payload, organizzatore);
    }


    @PutMapping("/{idEvento}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    public Evento updateEvento(@PathVariable UUID idEvento, @RequestBody EventoDTO payload) {

        return eventService.updateEvento(idEvento, payload);
    }

    @DeleteMapping("/{idEvento}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    public void deleteEvento(@PathVariable UUID idEvento) {
        eventService.deleteEvento(idEvento);
    }
}
