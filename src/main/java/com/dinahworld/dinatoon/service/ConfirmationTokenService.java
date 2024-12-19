package com.dinahworld.dinatoon.service;

import com.dinahworld.dinatoon.model.ConfirmationToken;
import com.dinahworld.dinatoon.model.User;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken, User user);

    ConfirmationToken findByConfirmationToken(String confirmationToken);

    void delete(User user);
}