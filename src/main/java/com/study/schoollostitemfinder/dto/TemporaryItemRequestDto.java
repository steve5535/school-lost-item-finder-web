package com.study.schoollostitemfinder.dto;

import lombok.Getter;

@Getter
public class TemporaryItemRequestDto {
    private String itemName;
    private String itemDetail;
    private String itemPlace;
    private String itemImg;
    private Boolean isAccept;
}
