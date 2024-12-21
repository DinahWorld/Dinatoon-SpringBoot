package com.dinahworld.dinatoon.service.impl;

import com.dinahworld.dinatoon.dto.DinatoonDto;
import com.dinahworld.dinatoon.exception.DinatoonException;
import com.dinahworld.dinatoon.exception.UserException;
import com.dinahworld.dinatoon.model.Dinatoon;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.repository.DinatoonRepository;
import com.dinahworld.dinatoon.service.DinatoonService;
import com.dinahworld.dinatoon.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DinatoonServiceImpl implements DinatoonService {
    private static final String DINATOON_NOT_FOUND = "Dinatoon not found with ID : ";
    private final DinatoonRepository dinatoonRepository;
    private final WebClient webClient;
    private final String ATTRIBUTES = "attributes";
    private final UserService userService;

    @Override
    @Transactional
    public Dinatoon createDinatoon(DinatoonDto dto) {
        if (dinatoonRepository.findByName(dto.getName()).isPresent()) {
            throw new DinatoonException("Dinatoon already exist");
        }
        return dinatoonRepository.save(toEntity(dto));
    }


    @Override
    public Dinatoon getDinatoonById(Long id) {
        return dinatoonRepository.findById(id).orElseThrow(() -> new DinatoonException(DINATOON_NOT_FOUND));
    }

    @Override
    @Transactional
    public void deleteDinatoon(Long id) {
        dinatoonRepository.deleteById(id);
    }

    @Override
    public List<Dinatoon> getAllDinatoons() {
        return dinatoonRepository.findAll();
    }

    @Override
    public Dinatoon updateDinatoon(Long id, DinatoonDto dto) {
        dinatoonRepository.findById(id).orElseThrow(() -> new UserException(DINATOON_NOT_FOUND));
        var dinatoon = toEntity(dto);
        dinatoon.setId(id);
        dinatoonRepository.deleteById(id);
        dinatoonRepository.save(dinatoon);

        return dinatoon;
    }

    @Override
    public Mono<List<Dinatoon>> searchDinatoon(String titleParam) {
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
    @Transactional
    public Dinatoon saveManga(DinatoonDto dto, User user) {
        var dinatoon = createDinatoon(dto);

        if (!user.getDinatoons().contains(dinatoon)) {
            user.getDinatoons().add(dinatoon);
            userService.saveUser(user);
            return dinatoon;
        } else {
            throw new UserException("Dinatoon already exists in User List");
        }
    }

    @Override
    @Transactional
    public List<Dinatoon> getAllUserDinatoon(User user) {
        return user.getDinatoons();
    }

    @Override
    @Transactional
    public void deleteUserDinatoon(User user, Long dinatoonId) {
        var dinatoon = getDinatoonById(dinatoonId);
        user.getDinatoons().remove(dinatoon);
        userService.saveUser(user);
    }



    private List<Dinatoon> mapJsonDataToDinatoon(JsonNode jsonNode) {
        var mangas = new ArrayList<Dinatoon>();

        jsonNode.get("data").forEach(mangaNode -> {
            var genre = mangaNode.get("type").asText();
            var title = extractText(mangaNode, ATTRIBUTES, "title", "en");
            var description = extractText(mangaNode, ATTRIBUTES, "description", "en");
            var lastChapter = parseLastChapter(mangaNode, ATTRIBUTES, "lastChapter");
            var imageUrl = extractCoverImage(mangaNode);

            mangas.add(Dinatoon.builder()
                    .name(title)
                    .genre(genre)
                    .imageUrl(imageUrl)
                    .description(description)
                    .totalChapters(lastChapter)
                    .build());
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

    private Dinatoon toEntity(DinatoonDto dto) {
        return Dinatoon.builder()
                .name(dto.getName())
                .genre(dto.getGenre())
                .imageUrl(dto.getImageUrl())
                .description(dto.getDescription())
                .totalChapters(dto.getTotalChapters())
                .build();
    }
}