package com.dinahworld.dinatoon.feature.userdinatoon.model;

import com.dinahworld.dinatoon.feature.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "user_dinatoons")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDinatoons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private UUID dinatoonId;

    private Double rating;

    @Column(length = 500)
    private String comment;

    private String currentChapter;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();

}
