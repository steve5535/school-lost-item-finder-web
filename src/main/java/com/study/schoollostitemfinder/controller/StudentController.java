package com.study.schoollostitemfinder.controller;

import com.study.schoollostitemfinder.dto.StudentRequestDto;
import com.study.schoollostitemfinder.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // 학생 등록
    @PostMapping("/student")
    public String singUp(@RequestBody StudentRequestDto requestDto) {
        return studentService.singUp(requestDto);
    }
}
