package com.pastrycertified.pastrycertified.api.src.repository;

import com.pastrycertified.pastrycertified.api.src.data.doOut.authentication.TokenData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Cette interface définit les opérations de base pour la manipulation des entités {@link TokenData}.
 */
public interface TokenRepository extends JpaRepository<TokenData, Integer> {

    /**
     * Récupère tous les tokens valides associés à un utilisateur spécifié.
     *
     * @param id L'identifiant de l'utilisateur.
     * @return La liste des tokens valides associés à l'utilisateur.
     */
    @Query(value = """
            select token from TokenData token inner join Users u
            on token.user.id = u.id
            where u.id = :id and (token.expired = false or token.revoked = false)
            """)
    List<TokenData> findAllValidTokenByUser(Integer id);

    /**
     * Recherche un token par sa valeur.
     *
     * @param token La valeur du token à rechercher.
     * @return Le token correspondant, s'il existe.
     */
    Optional<TokenData> findByToken(String token);
}

