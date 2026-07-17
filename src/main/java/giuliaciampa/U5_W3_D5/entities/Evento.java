package giuliaciampa.U5_W3_D5.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "eventi")
@Getter
@Setter
public class Evento {

    //ATTRIBUTI

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false, length = 60)
    private String titolo;


    private String descrizione;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false, length = 30)
    private String luogo;

    @Column(name = "numero_posti", nullable = false)
    private int numeroPosti;

    @Setter(AccessLevel.NONE)
    @JoinColumn(name = "id_organizzatore", nullable = false)
    @ManyToOne
    private Utente organizzatore;

    //COSTRUTTORE

    public Evento(String titolo, String descrizione, LocalDate data, String luogo, int numeroPosti, Utente organizzatore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.luogo = luogo;
        this.numeroPosti = numeroPosti;
        this.organizzatore = organizzatore;
    }


    //COSTRUTTORE VUOTO
    public Evento() {
    }

    //TOSTRING


    @Override
    public String toString() {
        return "Evento{" +
                "id=" + getId() +
                ", titolo='" + getTitolo() + '\'' +
                ", descrizione='" + getDescrizione() + '\'' +
                ", data=" + getData() +
                ", luogo='" + getLuogo() + '\'' +
                ", numeroPosti=" + getNumeroPosti() +
                ", organizzatore=" + getOrganizzatore() +
                '}';
    }
}
