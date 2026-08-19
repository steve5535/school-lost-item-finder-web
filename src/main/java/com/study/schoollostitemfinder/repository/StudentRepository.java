package com.study.schoollostitemfinder.repository;

import com.study.schoollostitemfinder.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentNumber(int studentNumber);


}
