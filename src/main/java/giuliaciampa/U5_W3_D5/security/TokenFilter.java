package giuliaciampa.U5_W3_D5.security;

import giuliaciampa.U5_W3_D5.entities.Utente;
import giuliaciampa.U5_W3_D5.exceptions.UnauthorizedException;
import giuliaciampa.U5_W3_D5.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TokenFilter extends OncePerRequestFilter {
    private final JWTTools jwtTools;
    private final UserService usersService;

    public TokenFilter(JWTTools jwtTools, UserService usersService) {
        this.jwtTools = jwtTools;
        this.usersService = usersService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire il token nell'authorization nel formato Bearer ");

        String accessToken = authHeader.replace("Bearer ", "");

        this.jwtTools.verifyToken(accessToken);

        //INFORMAZIONI UTENTE

        UUID userId = this.jwtTools.extractIdFromToken(accessToken);
        Utente authenticatedUser = this.usersService.findById(userId);

        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

}
