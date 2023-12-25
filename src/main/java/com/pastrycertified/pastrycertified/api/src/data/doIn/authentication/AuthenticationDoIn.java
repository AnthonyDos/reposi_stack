package com.pastrycertified.pastrycertified.api.src.data.doIn.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class AuthenticationDoIn {

    private String email;

    private String password;
}
