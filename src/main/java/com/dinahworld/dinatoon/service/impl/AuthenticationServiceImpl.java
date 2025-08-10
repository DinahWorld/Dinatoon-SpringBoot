package com.dinahworld.dinatoon.service.impl;


import com.dinahworld.dinatoon.dto.AuthenticateDto;
import com.dinahworld.dinatoon.dto.DinatoonResponse;
import com.dinahworld.dinatoon.exception.DinatoonException;
import com.dinahworld.dinatoon.exception.UserException;
import com.dinahworld.dinatoon.model.ConfirmationToken;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.service.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final String websiteUrl = "http://localhost:4321";
    private final String serverUrl = "http://localhost:4321";
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-secret}")
    private String clientSecret;

    @Override
    @Transactional
    public DinatoonResponse register(User user) {
        userService.saveUser(user);
        createTokenAndSendEmail(user);
        return new DinatoonResponse("Mail sent");
    }

    @Override
    public void createTokenAndSendEmail(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken, user);

        var confirmAccountMsg = "To confirm your account, please click here : " +
                serverUrl + "/api/public/v1/auth/confirm-account?token=" + confirmationToken.getToken();

        emailService.sendSimpleMessage(user.getEmail(), "Complete Registration !", confirmAccountMsg);
        log.info("Email send to {}", user.getEmail());
    }

    @Override
    public String authenticate(AuthenticateDto userCredentials) throws UserException {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(userCredentials.email(), userCredentials.password());
            Authentication authenticate = authenticationManager.authenticate(authToken);
            User user = (User) authenticate.getPrincipal();

            if (user.getEnabled().equals(Boolean.FALSE)) {
                log.error("User not confirmed ! {}", user.getId());
                throw new UserException("User not confirmed !");
            }
            String token = jwtService.generateToken(user);
            log.info("User {} is authenticated", user.getId());
            return token;

        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
    }


    @Override
    public DinatoonResponse confirmUser(String confirmationToken) {
        ConfirmationToken token = confirmationTokenService.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User user = userService.getUserByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            userService.saveUser(user);
            log.info("User {} has confirmed his account", user.getEmail());
            confirmationTokenService.delete(user);
            return new DinatoonResponse("Mail Confirmed");
        } else {
            log.error("The link is invalid or broken !");
            throw new DinatoonException("The link is invalid or broken !");
        }
    }

    @Override
    public String getGoogleOauthURL() {
        return new GoogleAuthorizationCodeRequestUrl(
                clientId,
                websiteUrl,
                Arrays.asList("email", "profile", "openid")
        ).build();
    }

    @Override
    public String getAccessOauthToken(String code) {
        try {
            var token = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    new GsonFactory(),
                    clientId,
                    clientSecret,
                    code,
                    websiteUrl
            ).execute();

            GoogleIdToken idToken = token.parseIdToken();
            GoogleIdToken.Payload payload = idToken.getPayload();


            String email = payload.getEmail();
            var user = userService.createUserOAuth2(email, "GOOGLE");

            return jwtService.generateToken(user);

        } catch (IOException e) {
            throw new DinatoonException("UNAUTHORIZED : 401", HttpStatus.UNAUTHORIZED);
        }
    }
}