package com.dinahworld.dinatoon.controller;

import com.dinahworld.dinatoon.dto.DinatoonDto;
import com.dinahworld.dinatoon.model.Dinatoon;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.service.DinatoonService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/dinatoons")
@RequiredArgsConstructor
public class DinatoonController {

    private final DinatoonService dinatoonService;

    @GetMapping("/public/search")
    @Operation(description = "Search Manga/Dinatoon in MangaDex")
    public ResponseEntity<Mono<List<Dinatoon>>> searchDinatoon(@RequestParam String title) {
        return ResponseEntity.ok(dinatoonService.searchDinatoon(title));
    }

    @GetMapping("/public/all")
    @Operation(description = "Get All Dinatoon")
    public ResponseEntity<List<Dinatoon>> getAllDinatoons() {
        return ResponseEntity.ok(dinatoonService.getAllDinatoons());
    }

    @GetMapping("/public")
    @Operation(description = "Get Dinatoon")
    public ResponseEntity<Dinatoon> getDinatoonById(@RequestParam Long id) {
        return ResponseEntity.ok(dinatoonService.getDinatoonById(id));
    }

    @PostMapping("/save/user")
    @Operation(description = "Save Manga from MangaDex data Dinatoon Database")
    public ResponseEntity<Dinatoon> saveManga(Authentication authentication, @ParameterObject @Valid DinatoonDto dto) {
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(dinatoonService.saveManga(dto, user));
    }

    @GetMapping("/all/user")
    @Operation(description = "Get All Dinatoon From User")
    public ResponseEntity<List<Dinatoon>> getAllDinatoonFromUser(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(dinatoonService.getAllUserDinatoon(user));
    }

    @DeleteMapping("/user")
    @Operation(description = "Delete Dinatoon From User")
    public ResponseEntity<Void> deleteDinatoonFromUser(Authentication authentication, @RequestParam Long dinatoonId) {
        var user = (User) authentication.getPrincipal();
        dinatoonService.deleteUserDinatoon(user, dinatoonId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin")
    @Operation(description = "Update Dinatoon")
    public ResponseEntity<Dinatoon> updateDinatoon(@RequestParam Long id, @ParameterObject @Valid DinatoonDto dto) {
        return ResponseEntity.ok(dinatoonService.updateDinatoon(id, dto));
    }

    @DeleteMapping("/admin")
    @Operation(description = "Delete Dinatoon")
    public ResponseEntity<Void> deleteDinatoon(@RequestParam Long id) {
        dinatoonService.deleteDinatoon(id);
        return ResponseEntity.noContent().build();
    }
}