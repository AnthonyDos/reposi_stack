package com.pastrycertified.pastrycertified.api.src.repository;

import com.pastrycertified.pastrycertified.api.src.data.doOut.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Cette interface définit les opérations de base pour la manipulation des entités {@link Users}.
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {

    /**
     * Recherche un utilisateur par son adresse e-mail.
     *
     * @param email L'adresse e-mail de l'utilisateur à rechercher.
     * @return L'utilisateur correspondant, s'il existe.
     */
    Optional<Users> findByEmail(String email);
}

