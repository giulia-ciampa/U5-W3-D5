package giuliaciampa.U5_W3_D5.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utenti")
@Getter
@Setter
public class Utente implements UserDetails {
    //ATTRIBUTI
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonIgnoreProperties({"credentialsNonExpired", "enabled", "accountNonExpired", "authorities", "accountNonLocked"})
    private UUID id;

    @Column(nullable = false, length = 20)
    private String nome;

    @Column(nullable = false, length = 20)
    private String cognome;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    //COSTRUTTORE
    public Utente(String email, String password, String nome, String cognome) {
        this.email = email;
        this.password = password;
        this.ruolo = Ruolo.UTENTE_NORMALE;
        this.nome = nome;
        this.cognome = cognome;
    }

    //COSTRUTTORE VUOTO
    public Utente() {
    }


    //TOSTRING

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", ruolo=" + ruolo +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}



