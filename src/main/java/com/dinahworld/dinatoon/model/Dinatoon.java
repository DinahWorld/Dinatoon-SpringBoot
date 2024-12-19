package com.dinahworld.dinatoon.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "dinatoons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dinatoon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String imageUrl;

    private String description;

    private Integer totalChapters;

}