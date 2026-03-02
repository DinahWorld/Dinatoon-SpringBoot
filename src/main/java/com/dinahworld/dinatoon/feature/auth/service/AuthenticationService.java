package com.dinahworld.dinatoon.feature.auth.service;

import com.dinahworld.dinatoon.feature.auth.dto.AuthenticateDto;
import com.dinahworld.dinatoon.feature.auth.dto.DinatoonResponse;
import com.dinahworld.dinatoon.feature.user.model.User;
import com.dinahworld.dinatoon.shared.exception.UserException;

public interface AuthenticationService {
    DinatoonResponse register(User user);

    void createTokenAndSendEmail(User user);

    String authenticate(AuthenticateDto userCredentials) throws UserException;

    DinatoonResponse confirmUser(String confirmationToken);

    String getGoogleOauthURL();

    String getAccessOauthToken(String code);
}