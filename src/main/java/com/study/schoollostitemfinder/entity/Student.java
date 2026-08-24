package com.study.schoollostitemfinder.entity;

import com.study.schoollostitemfinder.common.TimeStamped;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Student extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long studentId;

    private int studentNumber;
    private String studentName;

    public Student(int studentNumber, String studentName) {
        this.studentNumber = studentNumber;
        this.studentName = studentName;
    }
}
