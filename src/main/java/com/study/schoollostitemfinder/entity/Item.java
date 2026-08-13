package com.study.schoollostitemfinder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;
    private Long studentId;
    private String itemName;
    private String itemPlace;
    private String itemImg;
    private String signUpAt;

    public Item(String signUpAt, String itemImg, String itemPlace, String itemName, Long studentId) {
        this.signUpAt = signUpAt;
        this.itemImg = itemImg;
        this.itemPlace = itemPlace;
        this.itemName = itemName;
        this.studentId = studentId;
    }
}
