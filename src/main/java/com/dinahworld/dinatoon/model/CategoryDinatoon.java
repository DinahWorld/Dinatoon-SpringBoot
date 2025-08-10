package com.dinahworld.dinatoon.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category_dinatoon")
@Getter
@Setter
public class CategoryDinatoon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userDinatoons;

    private Long categoryId;

}