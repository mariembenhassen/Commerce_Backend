package com.magazin.commerce.services.auth;

import com.magazin.commerce.dto.SignupRequest;
import com.magazin.commerce.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);
    Boolean hasUserWithEmail(String email);
}
