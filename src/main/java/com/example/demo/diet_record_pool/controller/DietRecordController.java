package com.example.demo.diet_record_pool.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.diet_record_pool.DTO.DietRecordDTO;
import com.example.demo.diet_record_pool.entity.DietRecord;
import com.example.demo.diet_record_pool.service.DietRecordService;

@RestController
@RequestMapping("/api")
public class DietRecordController {
    private final DietRecordService dietRecordService;

    public DietRecordController(DietRecordService dietRecordService) {
        this.dietRecordService = dietRecordService;
    }

    @PostMapping("/diet-record")
    public ResponseEntity<String> saveDietRecord(@RequestBody DietRecordDTO dietRecordDTO) {
        // 這裡通常會從身份驗證中獲取用戶ID
        // 這裡我們暫時使用一個假的用戶ID
        Integer userId = 1; // 假設用戶ID為1，實際應根據身份驗證獲取

        DietRecord savedRecord = dietRecordService.saveDietRecord(dietRecordDTO, userId);

        return ResponseEntity.ok("飲食記錄已成功保存，ID: " + savedRecord.getId());
    }
}
