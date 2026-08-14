package com.study.schoollostitemfinder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long studentId;

    private int studentNumber;
    private String studentName;
    private String takeAt;

    public Student(int studentNumber, String studentName, String takeAt) {
        this.studentNumber = studentNumber;
        this.studentName = studentName;
        this.takeAt = takeAt;
    }
}
