package com.example.demo.diet_record_pool.repository;

import com.example.demo.diet_record_pool.entity.MealRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRecordRepository extends JpaRepository<MealRecord, Long> {
}
