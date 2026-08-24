package com.study.schoollostitemfinder.entity;

import com.study.schoollostitemfinder.common.TimeStamped;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TemporaryItem extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    private String itemName;
    private String itemDetail;
    private String itemPlace;
    private String itemImg;
    private Boolean isAccept;

    public TemporaryItem(String itemName, String itemDetail, String itemPlace, String itemImg, Boolean isAccept) {
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.itemPlace = itemPlace;
        this.itemImg = itemImg;
        this.isAccept = null;
    }
}
