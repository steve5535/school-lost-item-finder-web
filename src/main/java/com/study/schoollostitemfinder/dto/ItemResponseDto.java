package com.study.schoollostitemfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemResponseDto {
    private Long itemId;
    private String itemName;
    private String itemPlace;
    private String itemImg;
    private String signUpAt;
    private String student;
}
