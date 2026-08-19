package com.study.schoollostitemfinder.service;

import com.study.schoollostitemfinder.dto.StudentRequestDto;
import com.study.schoollostitemfinder.entity.Student;
import com.study.schoollostitemfinder.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    // 학생 등록
    @Transactional
    public String singUp(StudentRequestDto dto) {
        Student student = new Student(
                dto.getStudentNumber(),
                dto.getStudentName(),
                dto.getPassword()
        );
        studentRepository.save(student);

        return "등록완료";
    }
}
