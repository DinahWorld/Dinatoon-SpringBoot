package com.dinahworld.dinatoon.service;

import com.dinahworld.dinatoon.dto.AuthenticateDto;
import com.dinahworld.dinatoon.dto.DinatoonResponse;
import com.dinahworld.dinatoon.exception.UserException;
import com.dinahworld.dinatoon.model.User;

public interface AuthenticationService {
    DinatoonResponse register(User user);

    void createTokenAndSendEmail(User user);

    String authenticate(AuthenticateDto userCredentials) throws UserException;

    DinatoonResponse confirmUser(String confirmationToken);

    String getGoogleOauthURL();

    String getAccessOauthToken(String code);
}