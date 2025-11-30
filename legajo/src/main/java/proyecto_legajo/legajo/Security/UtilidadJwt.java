package proyecto_legajo.legajo.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utilidad para generar y validar JWT.
 * Todas las operaciones relacionadas con tokens están centralizadas aquí.
 */
@Component
public class UtilidadJwt {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Genera un token JWT con subject = username
    public String generarToken(String username) {
        Date ahora = new Date();
        Date exp = new Date(ahora.getTime() + expirationMs);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(ahora)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extrae el nombre de usuario del token
    public String extraerUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Valida que el token no esté expirado y tenga firma válida
    public boolean esTokenValido(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
