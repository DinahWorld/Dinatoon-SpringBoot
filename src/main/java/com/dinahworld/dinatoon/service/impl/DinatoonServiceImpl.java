package com.dinahworld.dinatoon.service.impl;

import com.dinahworld.dinatoon.dto.DinatoonDto;
import com.dinahworld.dinatoon.dto.DinatoonUserDto;
import com.dinahworld.dinatoon.dto.LittleDinatoonDto;
import com.dinahworld.dinatoon.service.DinatoonService;
import com.dinahworld.dinatoon.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DinatoonServiceImpl implements DinatoonService {
    private final WebClient webClient;
    private final String ATTRIBUTES = "attributes";
    private final UserService userService;


    @Override
    public Mono<List<DinatoonUserDto>> searchDinatoonByTitle(String titleParam) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/manga")
                        .queryParam("limit", 10)
                        .queryParam("title", titleParam)
                        .queryParam("includedTagsMode", "AND")
                        .queryParam("excludedTagsMode", "OR")
                        .queryParam("contentRating[]", "safe")
                        .queryParam("contentRating[]", "suggestive")
                        .queryParam("contentRating[]", "erotica")
                        .queryParam("order[latestUploadedChapter]", "desc")
                        .queryParam("includes[]", "cover_art")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(this::mapJsonDataToDinatoon);
    }

    @Override
    public Mono<List<DinatoonDto>> searchDinatoonById(UUID id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/manga/" + id)
                        .queryParam("includes[]", "cover_art")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(this::mapJsonDataToDinatoonById);
    }

    @Override
    public Mono<LittleDinatoonDto> searchLittleDinatoonById(UUID id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/manga/" + id)
                        .queryParam("includes[]", "cover_art")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(this::mapJsonDataToDinatoonForHome);
    }

    private LittleDinatoonDto mapJsonDataToDinatoonForHome(JsonNode jsonNode) {

        var id = UUID.fromString(jsonNode.get("data").get("id").asText());
        var title = extractText(jsonNode.get("data"), ATTRIBUTES, "title", "en");
        var imageUrl = extractCoverImage(jsonNode.get("data"));

        return LittleDinatoonDto.builder()
                .id(id)
                .name(title)
                .imageUrl(imageUrl)
                .build();
    }

    private List<DinatoonDto> mapJsonDataToDinatoonById(JsonNode jsonNode) {
        var mangas = new ArrayList<DinatoonDto>();

        var genre = jsonNode.get("data").get("type").asText();
        var id = UUID.fromString(jsonNode.get("data").get("id").asText());
        var title = extractText(jsonNode.get("data"), ATTRIBUTES, "title", "en");
        var description = extractText(jsonNode.get("data"), ATTRIBUTES, "description", "en");
        var lastChapter = parseLastChapter(jsonNode.get("data"), ATTRIBUTES, "lastChapter");
        var imageUrl = extractCoverImage(jsonNode.get("data"));

        mangas.add(DinatoonDto.builder()
                .id(id)
                .name(title)
                .genre(genre)
                .imageUrl(imageUrl)
                .description(description)
                .totalChapters(lastChapter)
                .build());

        return mangas;
    }

    private List<DinatoonUserDto> mapJsonDataToDinatoon(JsonNode jsonNode) {
        var mangas = new ArrayList<DinatoonUserDto>();

        jsonNode.get("data").forEach(mangaNode -> {
            var id = UUID.fromString(mangaNode.get("id").asText());
            var imageUrl = extractCoverImage(mangaNode);

            mangas.add(new DinatoonUserDto(id, imageUrl));
        });

        return mangas;
    }

    private String extractText(JsonNode root, String... keys) {
        JsonNode current = root;
        for (String key : keys) {
            if (current != null && current.has(key)) {
                current = current.get(key);
            } else {
                return "";
            }
        }
        return current.asText();
    }

    private int parseLastChapter(JsonNode root, String... keys) {
        String lastChapter = extractText(root, keys);
        try {
            return Integer.parseInt(lastChapter);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String extractCoverImage(JsonNode mangaNode) {
        if (mangaNode.has("relationships")) {
            for (JsonNode rel : mangaNode.get("relationships")) {
                if ("cover_art".equals(rel.get("type").asText()) && rel.has("attributes")) {
                    String fileName = rel.get("attributes").get("fileName").asText();
                    return "https://uploads.mangadex.org/covers/" + mangaNode.get("id").asText() + "/" + fileName;
                }
            }
        }
        return null;
    }
}