package com.study.schoollostitemfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentResponseDto {
    private Long studentId;
    private int studentNumber;
    private String studentName;
}
