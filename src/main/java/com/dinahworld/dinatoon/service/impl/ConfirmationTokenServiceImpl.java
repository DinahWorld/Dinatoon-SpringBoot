package com.dinahworld.dinatoon.service.impl;

import com.dinahworld.dinatoon.model.ConfirmationToken;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.repository.ConfirmationTokenRepository;
import com.dinahworld.dinatoon.service.ConfirmationTokenService;
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