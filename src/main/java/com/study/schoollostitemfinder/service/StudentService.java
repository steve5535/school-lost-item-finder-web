package com.study.schoollostitemfinder.service;

import com.study.schoollostitemfinder.dto.StudentRequestDto;
import com.study.schoollostitemfinder.dto.StudentResponseDto;
import com.study.schoollostitemfinder.entity.Student;
import com.study.schoollostitemfinder.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public void uploadExcel(MultipartFile file) throws IOException {

        List<Student> students = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())){
            Sheet sheet = workbook.getSheetAt(0);

            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if(row == null) {
                    continue;
                }
                int studentNumber = Integer.parseInt(formatter.formatCellValue(row.getCell(0)));
                String studentName = formatter.formatCellValue(row.getCell(1));

                Student student = new Student(studentNumber, studentName);

                students.add(student);
            }
        }

        studentRepository.saveAll(students);
    }
}
