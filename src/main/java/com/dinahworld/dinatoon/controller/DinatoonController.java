package com.dinahworld.dinatoon.controller;

import com.dinahworld.dinatoon.dto.DinatoonDto;
import com.dinahworld.dinatoon.model.Dinatoon;
import com.dinahworld.dinatoon.service.DinatoonService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/dinatoons")
@RequiredArgsConstructor
public class DinatoonController {

    private final DinatoonService dinatoonService;

    @GetMapping("/search")
    @Operation(description = "Search Manga/Dinatoon in MangaDex")
    public ResponseEntity<Mono<List<Dinatoon>>> searchDinatoon(@RequestParam String title) {
        return ResponseEntity.ok(dinatoonService.searchDinatoon(title));
    }

    @GetMapping
    @Operation(description = "Get All Dinatoon")
    public ResponseEntity<List<Dinatoon>> getAllDinatoons() {
        return ResponseEntity.ok(dinatoonService.getAllDinatoons());
    }

    @GetMapping("/{id}")
    @Operation(description = "Get Dinatoon")
    public ResponseEntity<Dinatoon> getDinatoonById(@PathVariable Long id) {
        return ResponseEntity.ok(dinatoonService.getDinatoonById(id));
    }

    @PostMapping
    @Operation(description = "Save Manga from MangaDex data Dinatoon Database")
    public ResponseEntity<Dinatoon> saveManga(@ParameterObject @Valid DinatoonDto dto) {
        return ResponseEntity.ok(dinatoonService.createDinatoon(dto));
    }

    @PutMapping("/{id}")
    @Operation(description = "Update Dinatoon")
    public ResponseEntity<Dinatoon> updateDinatoon(@PathVariable Long id, @ParameterObject @Valid DinatoonDto dto) {
        return ResponseEntity.ok(dinatoonService.updateDinatoon(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete Dinatoon")
    public ResponseEntity<Void> deleteDinatoon(@PathVariable Long id) {
        dinatoonService.deleteDinatoon(id);
        return ResponseEntity.noContent().build();
    }
}