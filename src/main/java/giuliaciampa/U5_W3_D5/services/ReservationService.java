package giuliaciampa.U5_W3_D5.services;

import giuliaciampa.U5_W3_D5.entities.Evento;
import giuliaciampa.U5_W3_D5.entities.Prenotazione;
import giuliaciampa.U5_W3_D5.entities.Utente;
import giuliaciampa.U5_W3_D5.exceptions.BadRequestException;
import giuliaciampa.U5_W3_D5.exceptions.NotFoundException;
import giuliaciampa.U5_W3_D5.repositories.EventRepository;
import giuliaciampa.U5_W3_D5.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    //ATTRIBUTI
    private final ReservationRepository reservationRepository;
    private final EventRepository eventRepository;

    public ReservationService(ReservationRepository reservationRepository, EventRepository eventRepository) {
        this.reservationRepository = reservationRepository;
        this.eventRepository = eventRepository;
    }

    // UTENTE LOGGATO PUO VEDERE LE SUE PRENOTAZIONI
    public List<Prenotazione> getReservation(Utente utente) {
        List<Prenotazione> lista = this.reservationRepository.findByUtente(utente);
        if (lista.isEmpty()) {
            throw new NotFoundException("la lista delle prenotazioni risulta vuota");
        }
        return lista;

    }

    //PRENOTAZIONE UTENTI
    public Prenotazione prenota(UUID idEvento, Utente utente) {
        Evento evento = eventRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento non trovato!"));

        int numeroPrenotazioniAttuali = reservationRepository.countByEventoId(idEvento);

        if (numeroPrenotazioniAttuali >= evento.getNumeroPosti()) {
            throw new BadRequestException("Posti esauriti per questo evento!");
        }

        Prenotazione nuovaPrenotazione = new Prenotazione(utente, evento);
        return reservationRepository.save(nuovaPrenotazione);
    }
}
