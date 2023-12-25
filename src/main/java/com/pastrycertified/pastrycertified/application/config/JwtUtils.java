package com.pastrycertified.pastrycertified.application.config;

import com.pastrycertified.pastrycertified.application.exceptions.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;


/**
 * Service fournissant des utilitaires pour la manipulation des jetons JWT (JSON Web Token).
 */
@Service
public class JwtUtils {

    private String SECRET_KEY = "${config.signin.key}";

    /**
     * Extrait le nom d'utilisateur du jeton JWT.
     *
     * @param token Le jeton JWT.
     * @return Le nom d'utilisateur extrait.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait la date d'expiration du jeton JWT.
     *
     * @param token Le jeton JWT.
     * @return La date d'expiration extraite.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Vérifie si le jeton JWT a une revendication spécifique.
     *
     * @param token     Le jeton JWT.
     * @param claimName Le nom de la revendication.
     * @return True si la revendication existe, sinon False.
     */
    public boolean hasClaim(String token, String claimName) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

    /**
     * Extrait une revendication spécifique du jeton JWT en utilisant un résolveur de revendication.
     *
     * @param token          Le jeton JWT.
     * @param claimsResolver Le résolveur de revendication.
     * @param <T>            Le type de la revendication extraite.
     * @return La revendication extraite.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrait toutes les revendications du jeton JWT.
     *
     * @param token Le jeton JWT.
     * @return Les revendications extraites.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtient la clé secrète utilisée pour signer et vérifier les jetons JWT.
     *
     * @return La clé secrète.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Vérifie si le jeton JWT est expiré.
     *
     * @param token Le jeton JWT.
     * @return True si le jeton est expiré, sinon False.
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Génère un nouveau jeton JWT pour l'utilisateur avec les revendications spécifiées.
     *
     * @param userDetails Les détails de l'utilisateur.
     * @param claims      Les revendications à inclure dans le jeton.
     * @return Le jeton JWT généré.
     */
    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        return createToken(claims, userDetails);
    }

    /**
     * Crée un nouveau jeton JWT avec les revendications spécifiées.
     *
     * @param claims      Les revendications à inclure dans le jeton.
     * @param userDetails Les détails de l'utilisateur.
     * @return Le jeton JWT créé.
     */
    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(8)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Vérifie la validité du jeton JWT par rapport aux détails de l'utilisateur.
     *
     * @param token       Le jeton JWT.
     * @param userDetails Les détails de l'utilisateur.
     * @return True si le jeton est valide, sinon False.
     * @throws JwtAuthenticationException Si une exception d'authentification JWT survient.
     */
    public Boolean isTokenValid(String token, UserDetails userDetails) throws JwtAuthenticationException {
        final String username = extractUsername(token);
        try {
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (SignatureException ex) {
            throw new JwtAuthenticationException("invalid Jwt signature", ex);
        }
    }
}
