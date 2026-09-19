package com.study.schoollostitemfinder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Item{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    private String itemName;
    private String itemDetail;
    private String itemPlace;
    private String itemImg;
    private LocalDateTime signUpAt;
    private LocalDateTime takeAt;

    @ManyToOne
    private Student student;

    public Item(String itemName, String itemDetail, String itemPlace, String itemImg, LocalDateTime signUpAt) {
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.itemPlace = itemPlace;
        this.itemImg = itemImg;
        this.signUpAt = signUpAt;
    }
}
