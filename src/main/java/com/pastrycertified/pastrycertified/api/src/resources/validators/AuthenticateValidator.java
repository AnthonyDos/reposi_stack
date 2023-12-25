package com.pastrycertified.pastrycertified.api.src.resources.validators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe utilitaire pour valider les champs lors de la connexion de l'utilisateur.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthenticateValidator {


//    public static void controllerAuthenticateRequest(final AuthenticationDoIn request) {
//
//        if (2 != calculateNumberCriteria(request)) {
//            throw new FunctionnalException(CODE_CHAMP_OBLIGATOIRE_MANQUANT, CODE_HTTP_BAD_REQUEST, MSG_CHAMP_OBLIGATOIRE);
//        }
//    }
//
//
//    private static int calculateNumberCriteria(final AuthenticationDoIn request) {
//        int numberCriteria = 0;
//
//        if (StringUtils.isNotBlank(request.getEmail())) numberCriteria++;
//        if (StringUtils.isNotBlank(request.getPassword())) numberCriteria++;
//
//        return numberCriteria;
//    }
}

