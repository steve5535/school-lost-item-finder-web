package com.study.schoollostitemfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ItemResponseDto {
    private Long itemId;
    private String itemName;
    private String itemDetail;
    private String itemPlace;
    private String itemImg;
    private LocalDateTime signUpAt;
    private LocalDateTime takeAt;
    private TakeStudentResponseDto student;
}
