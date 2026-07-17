package giuliaciampa.U5_W3_D5.services;

import giuliaciampa.U5_W3_D5.DTO.UserDTO;
import giuliaciampa.U5_W3_D5.entities.Ruolo;
import giuliaciampa.U5_W3_D5.entities.Utente;
import giuliaciampa.U5_W3_D5.exceptions.BadRequestException;
import giuliaciampa.U5_W3_D5.exceptions.NotFoundException;
import giuliaciampa.U5_W3_D5.repositories.ReservationRepository;
import giuliaciampa.U5_W3_D5.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final PasswordEncoder bcrypt;

    public UserService(UserRepository usersRepository, ReservationRepository reservationRepository, PasswordEncoder bcrypt) {
        this.userRepository = usersRepository;
        this.reservationRepository = reservationRepository;
        this.bcrypt = bcrypt;
    }

    //METODI

    //SALVA L'UTENTE
    public Utente saveUser(UserDTO payload) {
        //controllo che l'email non esista già

        if (this.userRepository.existsByEmail(payload.email()))
            throw new BadRequestException("l'email " + payload.email() + " esiste già.");

        //creo il nuovo oggetto

        Utente nuovoUtente = new Utente(payload.email(), this.bcrypt.encode(payload.password()), payload.nome(), payload.cognome());

        //salvo l'utente

        Utente utenteSalvato = this.userRepository.save(nuovoUtente);

        return utenteSalvato;
    }

    //FINDBYEMAIL
    public Utente findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato!"));
    }

    //FINDBYID

    public Utente findById(UUID userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("l'utente con id " + userId + " non è stato trovato"));
    }

    //INIZIALIZZARE UN ORGANIZZATORE
    public void inizializzaOrganizzatore() {
        System.out.println("Cerco l'utente nel DB...");
        userRepository.findByEmail("admin.admin2@gmail.com").ifPresentOrElse(utente -> {
            System.out.println("Utente trovato: " + utente.getEmail());
            utente.setRuolo(Ruolo.ORGANIZZATORE_DI_EVENTI);
            userRepository.save(utente);
            System.out.println("Ruolo aggiornato!");
        }, () -> System.out.println("ERRORE: Utente non trovato nel DB!"));
    }

    //METODO PER PROMUOVERE L'UTENTE AD ORGANIZZATORE
    public Utente promuoviADOrganizzatore(UUID idUtente) {

        //1. controllo se l'utente esiste
        Utente utente = userRepository.findById(idUtente)
                .orElseThrow(() -> new NotFoundException("Utente non trovato con ID: " + idUtente));

        // 2. Cambiamo il ruolo
        utente.setRuolo(Ruolo.ORGANIZZATORE_DI_EVENTI);

        // 3. Salviamo sul database
        return userRepository.save(utente);
    }


}

