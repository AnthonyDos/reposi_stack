package com.pastrycertified.pastrycertified.api.src.resources.authentication;

import com.pastrycertified.pastrycertified.api.src.data.doIn.authentication.AuthenticationDoIn;
import com.pastrycertified.pastrycertified.api.src.data.doOut.authentication.AuthenticationData;
import com.pastrycertified.pastrycertified.api.src.service.UserService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.auth}")
@RequiredArgsConstructor
@SwaggerDefinition(tags = @Tag(name = "AuthenticationResource", description = "Endpoints de la classe authentication."))
public class AuthenticationResource {

    private final UserService userService;

    @Operation(summary = "Création d'un nouvel utilisateur.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Utilisateur créé avec succès.",
            content = @Content(mediaType = "application/json"))
    @PostMapping("${auth.register}")
    public ResponseEntity<AuthenticationData> register(
            @RequestBody AuthenticationDoIn user
    ) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/authenticate")
    @ApiOperation(value = "Connexion au compte utilisateur.", notes = "Connexion au compte utilisateur.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Connexion succès."),
            @ApiResponse(code = 400, message = "Données incorrectes.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Erreur inattendue.", response = ErrorResponse.class)
    })
    public ResponseEntity<AuthenticationData> authenticate(@Valid @RequestBody final AuthenticationDoIn request) {

        // Validation des entrées.
//        AuthenticateValidator.controllerAuthenticateRequest(request);

        return ResponseEntity.ok(userService.authenticate(request));
    }
}

