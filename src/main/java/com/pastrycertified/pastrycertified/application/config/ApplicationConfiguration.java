package com.pastrycertified.pastrycertified.application.config;

import com.pastrycertified.pastrycertified.api.src.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe de configuration pour les beans spécifiques à l'application liés à la sécurité et à l'authentification.
 * Cette classe fournit des beans pour configurer le service de détails utilisateur, le gestionnaire d'authentification,
 * le fournisseur d'authentification et le codeur de mot de passe.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final UsersRepository usersRepository;

    /**
     * Définit un bean pour le service UserDetailsService, qui est responsable de la récupération des détails de l'utilisateur.
     *
     * @return Une implémentation de UserDetailsService.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    }

    /**
     * Définit un bean pour le AuthenticationManager en utilisant la configuration d'authentification fournie.
     *
     * @param config L'instance AuthenticationConfiguration.
     * @return Une instance de AuthenticationManager.
     * @throws Exception Si une erreur se produit lors de la configuration de AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Définit un bean pour le AuthenticationProvider, qui est responsable de l'authentification des utilisateurs.
     *
     * @return Une instance de AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Définit un bean pour le PasswordEncoder, qui est utilisé pour coder et vérifier les mots de passe.
     *
     * @return Une instance de PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
