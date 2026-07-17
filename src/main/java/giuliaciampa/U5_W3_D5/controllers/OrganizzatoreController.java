package giuliaciampa.U5_W3_D5.controllers;


import giuliaciampa.U5_W3_D5.entities.Utente;
import giuliaciampa.U5_W3_D5.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class OrganizzatoreController {

    //ATTRIBUTI
    private final UserService userService;

    public OrganizzatoreController(UserService userService) {
        this.userService = userService;
    }

    //INIZIALIZZA ORGANIZZATORE
    @PostMapping("/init-admin")
    public String setup() {
        userService.inizializzaOrganizzatore();
        return "organizzatore configurato correttamente";
    }

    //PROMOZIONE UTENTE
    @PatchMapping("/promuovi/{idUtente}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    public Utente promuovi(@PathVariable UUID idUtente) {
        return userService.promuoviADOrganizzatore(idUtente);
    }
}
