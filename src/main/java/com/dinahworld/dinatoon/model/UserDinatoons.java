package com.dinahworld.dinatoon.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "user_dinatoons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDinatoons implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @ManyToOne(targetEntity = Dinatoon.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "dinatoon_id", unique = true, nullable = false)
    private Dinatoon dinatoon;

    @ManyToOne(targetEntity = UserList.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "list_id", unique = true, nullable = false)
    private UserList list;

    private Integer userRating;

    private Integer currentChapter;
}