package com.pastrycertified.pastrycertified.api.src.service.serviceImpl;

import com.pastrycertified.pastrycertified.api.src.data.doIn.authentication.AuthenticationDoIn;
import com.pastrycertified.pastrycertified.api.src.data.doOut.authentication.AuthenticationData;
import com.pastrycertified.pastrycertified.api.src.data.doOut.users.Users;
import com.pastrycertified.pastrycertified.api.src.repository.UsersRepository;
import com.pastrycertified.pastrycertified.api.src.service.UserService;
import com.pastrycertified.pastrycertified.application.config.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "ROLE_USER";
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    public Integer save(final Users dto) {

        final Users user = Users.builder()
                .email(dto.getEmail())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .build();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }


    @Override
    @Transactional
    public AuthenticationData register(final AuthenticationDoIn dto) {

        final Users user = Users.builder()
                .email(dto.getEmail())
                .build();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        final var savedUser = userRepository.save(user);
        final Map<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("fullName", savedUser.getFirstname() + " " + savedUser.getLastname());
        final String token = jwtUtils.generateToken(savedUser, claims);
        final String expirationToken = String.valueOf(jwtUtils.extractExpiration(token));

        return AuthenticationData.builder()
                .id(savedUser.getId())
                .access_token(token)
                .role(ROLE_USER)
                .isAuthenticated(true)
                .expiration(expirationToken)
                .build();
    }


    @Override
    public AuthenticationData authenticate(final AuthenticationDoIn request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        final Users user = userRepository.findByEmail(request.getEmail()).get();
        final Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("fullName", user.getFirstname() + " " + user.getLastname());
        final String token = jwtUtils.generateToken(user, claims);
        final String expirationToken = String.valueOf(jwtUtils.extractExpiration(token));

        return AuthenticationData.builder()
                .id(user.getId())
                .access_token(token)
                .role(ROLE_USER)
                .isAuthenticated(true)
                .expiration(expirationToken)
                .build();
    }
}
