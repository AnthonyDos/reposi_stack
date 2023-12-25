package com.pastrycertified.pastrycertified.api.src.service;

import com.pastrycertified.pastrycertified.api.src.data.doIn.authentication.AuthenticationDoIn;
import com.pastrycertified.pastrycertified.api.src.data.doOut.authentication.AuthenticationData;

public interface UserService {

    AuthenticationData register(AuthenticationDoIn user);

    AuthenticationData authenticate(AuthenticationDoIn request);

}
