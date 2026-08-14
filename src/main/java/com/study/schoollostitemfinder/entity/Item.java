package com.study.schoollostitemfinder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    private String itemName;
    private String itemPlace;
    private String itemImg;
    private String signUpAt;

    @ManyToOne
    private Student student;

    public Item(String itemName, String itemPlace, String itemImg, String signUpAt, Student studentId) {
        this.itemName = itemName;
        this.itemPlace = itemPlace;
        this.itemImg = itemImg;
        this.signUpAt = signUpAt;
        this.student = studentId;
    }
}
