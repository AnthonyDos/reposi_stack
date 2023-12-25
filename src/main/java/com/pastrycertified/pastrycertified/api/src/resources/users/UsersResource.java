package com.pastrycertified.pastrycertified.api.src.resources.users;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Resource Rest des users.
 */
@RestController
@RequestMapping("${url.auth}")
@RequiredArgsConstructor
@Tag(name = "AuthenticationController", description = "Endpoints de la classe authentication.")
public class UsersResource {


}
