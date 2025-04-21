package com.example.demo.diet_record_pool.repository;

import com.example.demo.diet_record_pool.entity.DietRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DietRecordRepository extends JpaRepository<DietRecord, Long> {
    List<DietRecord> findByUserIdAndRecordDate(Integer userId, LocalDate recordDate);
}
