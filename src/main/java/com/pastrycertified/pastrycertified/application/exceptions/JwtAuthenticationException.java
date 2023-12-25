package com.pastrycertified.pastrycertified.application.exceptions;

import javax.security.sasl.AuthenticationException;

/**
 * Exception spécifique pour les erreurs d'authentification liées aux tokens JWT.
 */
public class JwtAuthenticationException extends AuthenticationException {

    /**
     * Construit une nouvelle exception avec le message d'erreur et la cause spécifiés.
     *
     * @param message Le message d'erreur décrivant l'exception.
     * @param cause   La cause sous-jacente de l'exception.
     */
    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construit une nouvelle exception avec le message d'erreur spécifié.
     *
     * @param message Le message d'erreur décrivant l'exception.
     */
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
