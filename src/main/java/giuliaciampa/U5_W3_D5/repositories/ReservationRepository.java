package giuliaciampa.U5_W3_D5.repositories;

import giuliaciampa.U5_W3_D5.entities.Prenotazione;
import giuliaciampa.U5_W3_D5.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Prenotazione, UUID> {
    List<Prenotazione> findByUtente(Utente utente);

    int countByEventoId(UUID eventoId);
}
