package giuliaciampa.U5_W3_D5.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
public class Prenotazione {

    //ATTRIBUTI

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @JoinColumn(name = "id_utente", nullable = false)
    @ManyToOne
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    //COSTRUTTORE

    public Prenotazione(Utente utente, Evento evento) {
        this.utente = utente;
        this.evento = evento;
    }

    //COSTRUTTOREVUOTO

    public Prenotazione() {
    }

    //TOSTRING
    @Override
    public String toString() {
        return "Prenotazione{" +
                "id=" + getId() +
                ", utente=" + getUtente() +
                ", evento=" + getEvento() +
                '}';
    }
}
