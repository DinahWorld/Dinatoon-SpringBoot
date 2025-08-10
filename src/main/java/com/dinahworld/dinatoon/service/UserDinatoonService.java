package com.dinahworld.dinatoon.service;

import com.dinahworld.dinatoon.dto.UserDinatoonDto;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.model.UserDinatoons;

import java.util.List;
import java.util.UUID;

public interface UserDinatoonService {
    UserDinatoons saveUserDinatoon(Long userId, UUID dinatoonId, Double rating, String comment, String currentChapter);

    UserDinatoons getUserDinatoonByDinatoonId(UUID dinatoonId);

    List<UserDinatoons> getAllUserDinatoonByUserId(Long userId);

    UserDinatoons saveManga(UserDinatoonDto dto, User user);

    UserDinatoons saveMangaByDefault(UUID uuid, User user);

    UserDinatoons getUserDinatoons(UUID dinatoonId);
}