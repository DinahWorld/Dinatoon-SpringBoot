package com.dinahworld.dinatoon.feature.auth.service.impl;

import com.dinahworld.dinatoon.feature.auth.model.ConfirmationToken;
import com.dinahworld.dinatoon.feature.auth.repository.ConfirmationTokenRepository;
import com.dinahworld.dinatoon.feature.auth.service.ConfirmationTokenService;
import com.dinahworld.dinatoon.feature.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken token, User user) {
        var confirmationToken = confirmationTokenRepository.findByUser(user);
        if (confirmationToken.isPresent()) {
            confirmationTokenRepository.delete(confirmationToken.get());
            log.info("old confirmation token was deleted");
        }
        confirmationTokenRepository.save(token);
        log.info("Inserted Confirmation Token {}", token.getToken());
    }

    @Override
    public ConfirmationToken findByConfirmationToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public void delete(User user) {
        var confirmationToken = confirmationTokenRepository.findByUser(user);
        if (confirmationToken.isPresent()) {
            confirmationTokenRepository.delete(confirmationToken.get());
            log.info("Confirmed token deleted successfully with ID: {}", confirmationToken.get().getId());
        } else {
            log.error("Confirmed token not found. Deletion failed.");
        }
    }

}