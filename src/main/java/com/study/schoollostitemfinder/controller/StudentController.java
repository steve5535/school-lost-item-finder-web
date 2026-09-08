package com.study.schoollostitemfinder.controller;

import com.study.schoollostitemfinder.dto.StudentRequestDto;
import com.study.schoollostitemfinder.dto.StudentResponseDto;
import com.study.schoollostitemfinder.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    // 엑셀에서 학생 불러오기
    @PostMapping("/student/excel-upload")
    public void excelUpload(@RequestParam("file")MultipartFile file) throws IOException {
            studentService.uploadExcel(file);
    }
}
