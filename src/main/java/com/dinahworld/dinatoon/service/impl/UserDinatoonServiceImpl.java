package com.dinahworld.dinatoon.service.impl;

import com.dinahworld.dinatoon.dto.UserDinatoonDto;
import com.dinahworld.dinatoon.model.CategoryDinatoon;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.model.UserDinatoons;
import com.dinahworld.dinatoon.repository.CategoryDinatoonRepository;
import com.dinahworld.dinatoon.repository.UserDinatoonsRepository;
import com.dinahworld.dinatoon.service.UserDinatoonService;
import com.dinahworld.dinatoon.service.UserService;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDinatoonServiceImpl implements UserDinatoonService {
    private final UserDinatoonsRepository userDinatoonsRepository;
    private final UserService userService;
    private final CategoryDinatoonRepository userDinatoonsCategoryRepository;

    @Override
    public UserDinatoons saveUserDinatoon(Long userId, UUID dinatoonId, Double rating, String comment, String currentChapter) {
        UserDinatoons userDinatoons = new UserDinatoons();
        userDinatoons.setUser(userService.getUserById(userId));
        userDinatoons.setDinatoonId(dinatoonId);
        userDinatoons.setRating(rating);
        userDinatoons.setComment(comment);
        userDinatoons.setCurrentChapter(currentChapter);

        var userDinatoon = userDinatoonsRepository.save(userDinatoons);
        saveUserDinatoonCategory(userDinatoon);


        return userDinatoon;
    }

    private void saveUserDinatoonCategory(UserDinatoons userDinatoons) {
        CategoryDinatoon link = new CategoryDinatoon();
        link.setUserDinatoons(userDinatoons.getId());
        link.setCategoryId(1L);
        userDinatoonsCategoryRepository.save(link);
    }

    @Override
    public UserDinatoons getUserDinatoonByDinatoonId(UUID dinatoonId) {
        return userDinatoonsRepository.findByDinatoonId(dinatoonId).orElseThrow(() -> new NoSuchElementException("Dinatoon not found"));
    }

    @Override
    public List<UserDinatoons> getAllUserDinatoonByUserId(Long userId) {
        return userDinatoonsRepository.findAllByUserId(userId).orElseThrow(() -> new NoSuchElementException("Dinatoon not found"));
    }

    @Override
    @Transactional
    public UserDinatoons saveManga(UserDinatoonDto dto, User user) {
        var dinatoon = toEntity(dto, user);
        userDinatoonsRepository.save(dinatoon);

        return dinatoon;
    }

    @Override
    @Transactional
    public UserDinatoons saveMangaByDefault(UUID uuid, User user) {
        var defDinatoon = UserDinatoonDto.builder()
                .dinatoonId(uuid)
                .comment(Strings.EMPTY)
                .rating(5.0)
                .listId(1L)
                .currentChapter("0")
                .build();

        var dinatoon = toEntity(defDinatoon, user);
        userDinatoonsRepository.save(dinatoon);
        saveUserDinatoonCategory(dinatoon);

        return dinatoon;
    }

    @Override
    public UserDinatoons getUserDinatoons(UUID dinatoonId) {
        return userDinatoonsRepository.findByDinatoonId(dinatoonId).orElseThrow(() -> new NoSuchElementException("There is no dinatoon with this id : " + dinatoonId));
    }

    private UserDinatoons toEntity(UserDinatoonDto dto, User user) {
        var dinatoon = new UserDinatoons();
        dinatoon.setCurrentChapter(dinatoon.getCurrentChapter());
        dinatoon.setUser(user);
        dinatoon.setComment(dto.getComment());
        dinatoon.setRating(dto.getRating());
        dinatoon.setDinatoonId(dto.getDinatoonId());
        return dinatoon;
    }
}