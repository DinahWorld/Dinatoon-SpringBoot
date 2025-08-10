package com.dinahworld.dinatoon.service.impl;

import com.dinahworld.dinatoon.dto.CategoryDinatoonResponse;
import com.dinahworld.dinatoon.dto.LittleDinatoonDto;
import com.dinahworld.dinatoon.exception.DinatoonException;
import com.dinahworld.dinatoon.model.Category;
import com.dinahworld.dinatoon.model.CategoryDinatoon;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.model.UserDinatoons;
import com.dinahworld.dinatoon.repository.CategoryRepository;
import com.dinahworld.dinatoon.repository.UserDinatoonsRepository;
import com.dinahworld.dinatoon.service.CategoryDinatoonService;
import com.dinahworld.dinatoon.service.CategoryService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryDinatoonService categoryDinatoonService;
    private final UserDinatoonsRepository userDinatoonsRepository;
    private final WebClient webClient;
    private final String ATTRIBUTES = "attributes";

    @Override
    public Category createCategory(String name, User user) {
        return categoryRepository.save(new Category(name, user));
    }


    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Category updateCategoryName(Long id, String name) {
        var category = getCategory(id);
        category.setName(name);
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new DinatoonException("Category not found"));
    }

    @Override
    public List<Category> getAllUserCategory(Long userId) {
        return categoryRepository.findAllByUserId(userId).orElseThrow(() -> new DinatoonException("Category not found"));
    }

    @Override
    public List<CategoryDinatoonResponse> getCategoryDinatoon(User user) {
        var categories = getAllUserCategory(user.getId());
        List<CategoryDinatoonResponse> responses = new ArrayList<>();

        for (Category category : categories) {
            CategoryDinatoonResponse response = new CategoryDinatoonResponse();
            response.setName(category.getName());
            response.setId(category.getId());

            List<CategoryDinatoon> userDinatoonCategories = categoryDinatoonService.getAllByCategoryId(category.getId());

            List<UserDinatoons> allUserDinatoons = new ArrayList<>();
            for (CategoryDinatoon udc : userDinatoonCategories) {
                List<UserDinatoons> userDinatoons = userDinatoonsRepository.findAllByUserId(udc.getUserDinatoons()).orElseThrow(() -> new NoSuchElementException("Dinatoon not found"));
                allUserDinatoons.addAll(userDinatoons);
            }

            var c = allUserDinatoons.stream()
                    .map(ud -> searchLittleDinatoonById(ud.getDinatoonId()).block())
                    .toList();

            response.setUserDinatoon(c);

            responses.add(response);
        }

        return responses;
    }

    private Mono<LittleDinatoonDto> searchLittleDinatoonById(UUID id) {
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