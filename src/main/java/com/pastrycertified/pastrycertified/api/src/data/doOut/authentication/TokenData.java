package com.pastrycertified.pastrycertified.api.src.data.doOut.authentication;

import com.pastrycertified.pastrycertified.api.src.data.doOut.users.Users;
import com.pastrycertified.pastrycertified.api.src.utils.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TokenData {

    @Id
    @GeneratedValue
    public Integer id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    /**
     * Utilisateur associé au jeton (relation many-to-one avec la classe Users).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public Users user;
}
