package giuliaciampa.U5_W3_D5.services;

import giuliaciampa.U5_W3_D5.DTO.EventoDTO;
import giuliaciampa.U5_W3_D5.entities.Evento;
import giuliaciampa.U5_W3_D5.entities.Ruolo;
import giuliaciampa.U5_W3_D5.entities.Utente;
import giuliaciampa.U5_W3_D5.exceptions.AccessDeniedException;
import giuliaciampa.U5_W3_D5.exceptions.DuplicateRequestException;
import giuliaciampa.U5_W3_D5.exceptions.NotFoundException;
import giuliaciampa.U5_W3_D5.repositories.EventRepository;
import giuliaciampa.U5_W3_D5.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;


    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;

    }


    //METODO SAVE
    public Evento save(EventoDTO payload, Utente organizzatore) {
        if (!organizzatore.getRuolo().equals(Ruolo.ORGANIZZATORE_DI_EVENTI))
            throw new AccessDeniedException("Solo gli utenti con ruolo ORGANIZZATORE possono creare eventi!");

        if (eventRepository.existsByTitoloAndLuogoAndData(payload.titolo(), payload.luogo(), payload.data())) {
            throw new DuplicateRequestException("Esiste già un evento con lo stesso titolo in questo luogo!");
        }

        Evento nuovoEvento = new Evento(
                payload.titolo(),
                payload.descrizione(),
                payload.data(),
                payload.luogo(),
                payload.numeroPosti(),
                organizzatore
        );

        return eventRepository.save(nuovoEvento);

    }


    // METODO PER ELIMINAZIONE
    public void deleteEvento(UUID idEvento) {
        Evento evento = eventRepository.findById(idEvento)
                .orElseThrow(() -> new NotFoundException("Evento non trovato!"));

        eventRepository.delete(evento);
    }

    //METODO PER MODIFICA
    public Evento updateEvento(UUID id, EventoDTO payload) {
        Evento eventoTrovato = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento con id " + id + " non trovato!"));

        eventoTrovato.setTitolo(payload.titolo());
        eventoTrovato.setDescrizione(payload.descrizione());
        eventoTrovato.setData(payload.data());
        eventoTrovato.setLuogo(payload.luogo());
        eventoTrovato.setNumeroPosti(payload.numeroPosti());

        return eventRepository.save(eventoTrovato);
    }

}


