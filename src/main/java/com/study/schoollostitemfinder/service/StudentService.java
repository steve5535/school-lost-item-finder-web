package com.study.schoollostitemfinder.service;

import com.study.schoollostitemfinder.dto.StudentRequestDto;
import com.study.schoollostitemfinder.dto.StudentResponseDto;
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
    public StudentResponseDto singUp(StudentRequestDto dto) {
        Student student = new Student(
                dto.getStudentNumber(),
                dto.getStudentName()
        );
        studentRepository.save(student);

        StudentResponseDto responseDto = new StudentResponseDto(
                student.getStudentId(),
                student.getStudentNumber(),
                student.getStudentName()
        );

        return responseDto;
    }

    // 학생 삭제(관리자)
    @Transactional
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }
}
