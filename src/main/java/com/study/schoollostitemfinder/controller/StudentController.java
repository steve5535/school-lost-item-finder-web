package com.study.schoollostitemfinder.controller;

import com.study.schoollostitemfinder.dto.StudentRequestDto;
import com.study.schoollostitemfinder.dto.StudentResponseDto;
import com.study.schoollostitemfinder.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    // 학생 등록
    @PostMapping("/student")
    public StudentResponseDto singUp(@RequestBody StudentRequestDto requestDto) {
        log.info("학생 등록 완료");
        return studentService.singUp(requestDto);
    }

    // 학생 삭제
    @DeleteMapping("/student/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        log.info("학생 삭제 완료");
    }
}
