package com.study.schoollostitemfinder.repository;

import com.study.schoollostitemfinder.entity.TemporaryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryItemRepository extends JpaRepository<TemporaryItem, Long> {
}
