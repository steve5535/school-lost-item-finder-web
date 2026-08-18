package com.study.schoollostitemfinder.repository;

import com.study.schoollostitemfinder.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
