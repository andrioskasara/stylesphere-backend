package com.stylesphere.service;

import com.stylesphere.model.dto.SignupRequest;
import com.stylesphere.model.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);

    void createAdminAccount();
}
