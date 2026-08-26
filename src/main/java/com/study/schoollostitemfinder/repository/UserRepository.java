package com.study.schoollostitemfinder.repository;

import com.study.schoollostitemfinder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
