package com.pastrycertified.pastrycertified.api.src.data.doOut.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationData {

    private Integer id;
    private String access_token;
    private String role;
    private boolean isAuthenticated;
    private String expiration;

    private String error;
}
