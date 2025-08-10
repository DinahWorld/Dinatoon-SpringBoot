package com.dinahworld.dinatoon.controller;

import com.dinahworld.dinatoon.dto.*;
import com.dinahworld.dinatoon.dto.mapper.UserAuthDtoMapper;
import com.dinahworld.dinatoon.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {
    private final UserAuthDtoMapper mapper;
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    @Operation(summary = "Register User")
    @ApiResponse(responseCode = "200", description = "Please confirm your email")
    public ResponseEntity<DinatoonResponse> register(@Valid @RequestBody UserAuthDto dto) {
        var user = mapper.apply(dto);
        var response = authenticationService.register(user);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/url")
    @Operation(summary = "Google OAuth")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<UrlDto> auth() {
        var url = authenticationService.getGoogleOauthURL();
        return ResponseEntity.ok(new UrlDto(url));
    }

    @GetMapping("/callback")
    @Operation(summary = "Callback")
    public ResponseEntity<AuthenticationResponse> callback(@RequestParam("code") String code) {
        var token = authenticationService.getAccessOauthToken(code);
        return ResponseEntity.ok(new AuthenticationResponse(token, "Authenticated"));

    }

    @PostMapping("/login")
    @Operation(summary = "Login User")
    @ApiResponse(responseCode = "200", description = "User logged")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticateDto dto) {
        var token = authenticationService.authenticate(dto);
        return ResponseEntity.ok(new AuthenticationResponse(token, "Authenticated"));
    }

    @GetMapping("/confirm-account")
    @Operation(summary = "Confirm Account via mail")
    @ApiResponse(responseCode = "200", description = "User confirmed")
    public ResponseEntity<DinatoonResponse> confirmUser(@RequestParam("token") String confirmationToken) {
        var response = authenticationService.confirmUser(confirmationToken);
        if (Objects.isNull(response)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("resend/confirm-mail")
    @Operation(summary = "Resend via mail confirm Mail")
    @ApiResponse(responseCode = "200", description = "Mail Sent")
    public ResponseEntity<DinatoonResponse> resendMail(@Valid @RequestBody UserAuthDto dto) {
        var user = mapper.apply(dto);
        authenticationService.createTokenAndSendEmail(user);
        return ResponseEntity.ok().body(new DinatoonResponse("Mail Sent"));
    }

}