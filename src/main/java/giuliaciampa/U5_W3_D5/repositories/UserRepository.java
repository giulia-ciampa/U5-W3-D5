package giuliaciampa.U5_W3_D5.repositories;

import giuliaciampa.U5_W3_D5.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Utente, UUID> {
    //registrazione
    boolean existsByEmail(String email);

    //login
    Optional<Utente> findByEmail(String email);
}
