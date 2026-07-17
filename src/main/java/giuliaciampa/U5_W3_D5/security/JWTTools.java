package giuliaciampa.U5_W3_D5.security;

import giuliaciampa.U5_W3_D5.entities.Utente;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    //ATTRIBUTO
    private final String secret;

    //COSTRUTTORE
    public JWTTools(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    //GENERA TOKEN
    public String generateToken(Utente utente) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 8))
                .subject(String.valueOf(utente.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}

