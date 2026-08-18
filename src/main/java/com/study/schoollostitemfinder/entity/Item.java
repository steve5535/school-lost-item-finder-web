package com.study.schoollostitemfinder.entity;

import com.study.schoollostitemfinder.common.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class Item extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    private String itemName;
    private String itemDetail;
    private String itemPlace;
    private String itemImg;
    private String takeAt;

    @ManyToOne
    private Student student;

    public Item(String itemName, String itemDetail, String itemPlace, String itemImg) {
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.itemPlace = itemPlace;
        this.itemImg = itemImg;
    }
}
