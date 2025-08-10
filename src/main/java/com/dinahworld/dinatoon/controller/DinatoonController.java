package com.dinahworld.dinatoon.controller;

import com.dinahworld.dinatoon.dto.DinatoonDto;
import com.dinahworld.dinatoon.dto.DinatoonUserDto;
import com.dinahworld.dinatoon.service.DinatoonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dinatoons")
@RequiredArgsConstructor
public class DinatoonController {

    private final DinatoonService dinatoonService;

    @GetMapping("/public/search")
    @Operation(description = "Search Manga/Dinatoon in MangaDex by Title")
    public ResponseEntity<Mono<List<DinatoonUserDto>>> searchDinatoonByTitle(@RequestParam String title) {
        return ResponseEntity.ok(dinatoonService.searchDinatoonByTitle(title));
    }

    @GetMapping("/public/search/{id}")
    @Operation(description = "Search Manga/Dinatoon in MangaDex by Id")
    public ResponseEntity<Mono<List<DinatoonDto>>> searchDinatoonById(@PathVariable UUID id) {
        return ResponseEntity.ok(dinatoonService.searchDinatoonById(id));
    }

}