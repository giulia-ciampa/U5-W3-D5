package giuliaciampa.U5_W3_D5.repositories;

import giuliaciampa.U5_W3_D5.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Evento, UUID> {

    boolean existsByTitoloAndLuogoAndData(String titolo, String luogo, LocalDate data);
}
