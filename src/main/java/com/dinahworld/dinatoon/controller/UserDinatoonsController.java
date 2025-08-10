package com.dinahworld.dinatoon.controller;

import com.dinahworld.dinatoon.dto.UserDinatoonDto;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.model.UserDinatoons;
import com.dinahworld.dinatoon.service.UserDinatoonService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user-dinatoons")
@RequiredArgsConstructor
public class UserDinatoonsController {

    private final UserDinatoonService dinatoonService;

    @PostMapping
    public ResponseEntity<UserDinatoons> addUserDinatoons(@RequestParam Long userId,
                                                          @RequestParam UUID dinatoonId,
                                                          @RequestParam(required = false) Double rating,
                                                          @RequestParam(required = false) String comment,
                                                          @RequestParam(required = false) String currentChapter) {
        UserDinatoons userDinatoon = dinatoonService.saveUserDinatoon(userId, dinatoonId, rating, comment, currentChapter);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDinatoon);
    }

    @GetMapping("/dinatoon")
    public UserDinatoons getUserDinatoonsByDinatoon(@RequestParam UUID dinatoonId) {
        return dinatoonService.getUserDinatoonByDinatoonId(dinatoonId);
    }

    @PostMapping("/save/user")
    @Operation(description = "Save Manga from MangaDex data Dinatoon Database")
    public ResponseEntity<UserDinatoons> saveManga(Authentication authentication, @ParameterObject @Valid UserDinatoonDto dto) {
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(dinatoonService.saveManga(dto, user));
    }

}