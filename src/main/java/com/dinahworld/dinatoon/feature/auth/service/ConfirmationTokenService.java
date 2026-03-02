package com.dinahworld.dinatoon.feature.auth.service;

import com.dinahworld.dinatoon.feature.auth.model.ConfirmationToken;
import com.dinahworld.dinatoon.feature.user.model.User;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken, User user);

    ConfirmationToken findByConfirmationToken(String confirmationToken);

    void delete(User user);
}