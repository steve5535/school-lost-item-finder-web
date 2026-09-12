package com.study.schoollostitemfinder.service;

import com.study.schoollostitemfinder.dto.StudentRequestDto;
import com.study.schoollostitemfinder.dto.StudentResponseDto;
import com.study.schoollostitemfinder.entity.Student;
import com.study.schoollostitemfinder.repository.ItemRepository;
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
    private final ItemRepository itemRepository;

    // 학생 추가
    @Transactional
    public StudentResponseDto singUp(StudentRequestDto requestDto) {
        Student student  = new Student(
                requestDto.getStudentNumber(),
                requestDto.getStudentName()
        );
        studentRepository.save(student);

        StudentResponseDto responseDto = new StudentResponseDto(
                student.getStudentId(),
                student.getStudentNumber(),
                student.getStudentName()
        );

        return responseDto;
    }

    // 엑셀로 학생 추가(가져가기 완료된 분실물 삭제, db에 있던 학생을 삭제)
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

        itemRepository.deleteByTakeAtIsNotNull();
        studentRepository.deleteAll();
        studentRepository.saveAll(students);
    }
}
