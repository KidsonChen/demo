package com.example.demo.diet_record_pool.service;

import com.example.demo.diet_record_pool.DTO.DietRecordDTO;
import com.example.demo.diet_record_pool.DTO.FoodItemDTO;
import com.example.demo.diet_record_pool.DTO.MealDTO;
import com.example.demo.diet_record_pool.entity.DietRecord;
import com.example.demo.diet_record_pool.entity.FoodItem;
import com.example.demo.diet_record_pool.entity.MealRecord;
import com.example.demo.diet_record_pool.repository.DietRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class DietRecordService {
    @Autowired
    private DietRecordRepository dietRecordRepository;

    @Transactional
    public DietRecord saveDietRecord(DietRecordDTO dietRecordDTO, Integer userId) {
        // 創建飲食記錄
        DietRecord dietRecord = new DietRecord();
        dietRecord.setUserId(userId);
        dietRecord.setRecordDate(LocalDate.now());
        
        // 添加早餐
        if (dietRecordDTO.getBreakfast() != null) {
            MealRecord breakfast = createMealRecord(dietRecord, "breakfast", dietRecordDTO.getBreakfast());
            dietRecord.getMealRecords().add(breakfast);
        }
        
        // 添加午餐
        if (dietRecordDTO.getLunch() != null) {
            MealRecord lunch = createMealRecord(dietRecord, "lunch", dietRecordDTO.getLunch());
            dietRecord.getMealRecords().add(lunch);
        }
        
        // 添加晚餐
        if (dietRecordDTO.getDinner() != null) {
            MealRecord dinner = createMealRecord(dietRecord, "dinner", dietRecordDTO.getDinner());
            dietRecord.getMealRecords().add(dinner);
        }
        
        // 添加點心
        if (dietRecordDTO.getSnacks() != null && !dietRecordDTO.getSnacks().isEmpty()) {
            MealRecord snack = new MealRecord();
            snack.setDietRecord(dietRecord);
            snack.setMealType("snack");
            
            for (FoodItemDTO foodItemDTO : dietRecordDTO.getSnacks()) {
                FoodItem foodItem = new FoodItem();
                foodItem.setMealRecord(snack);
                foodItem.setFoodName(foodItemDTO.getFood());
                foodItem.setPortion(foodItemDTO.getPortion());
                snack.getFoodItems().add(foodItem);
            }
            
            dietRecord.getMealRecords().add(snack);
        }
        
        // 添加水果
        if (dietRecordDTO.getFruits() != null && !dietRecordDTO.getFruits().isEmpty()) {
            MealRecord fruit = new MealRecord();
            fruit.setDietRecord(dietRecord);
            fruit.setMealType("fruit");
            
            for (FoodItemDTO foodItemDTO : dietRecordDTO.getFruits()) {
                FoodItem foodItem = new FoodItem();
                foodItem.setMealRecord(fruit);
                foodItem.setFoodName(foodItemDTO.getFood());
                foodItem.setPortion(foodItemDTO.getPortion());
                fruit.getFoodItems().add(foodItem);
            }
            
            dietRecord.getMealRecords().add(fruit);
        }
        
        return dietRecordRepository.save(dietRecord);
    }
    
    private MealRecord createMealRecord(DietRecord dietRecord, String mealType, MealDTO mealDTO) {
        MealRecord mealRecord = new MealRecord();
        mealRecord.setDietRecord(dietRecord);
        mealRecord.setMealType(mealType);
        
        if (mealDTO.getTime() != null && !mealDTO.getTime().isEmpty()) {
            try {
                LocalTime mealTime = LocalTime.parse(mealDTO.getTime(), DateTimeFormatter.ofPattern("HH:mm"));
                mealRecord.setMealTime(mealTime);
            } catch (Exception e) {
                // 處理時間格式錯誤
            }
        }
        
        if (mealDTO.getItems() != null) {
            for (FoodItemDTO foodItemDTO : mealDTO.getItems()) {
                FoodItem foodItem = new FoodItem();
                foodItem.setMealRecord(mealRecord);
                foodItem.setFoodName(foodItemDTO.getFood());
                foodItem.setPortion(foodItemDTO.getPortion());
                mealRecord.getFoodItems().add(foodItem);
            }
        }
        
        return mealRecord;
    }
}
