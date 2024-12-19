package com.dinahworld.dinatoon.repository;

import com.dinahworld.dinatoon.model.ConfirmationToken;
import com.dinahworld.dinatoon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    ConfirmationToken findByToken(String confirmationToken);

    Optional<ConfirmationToken> findByUser(User user);
}