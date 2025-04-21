package com.example.demo.diet_record_pool.DTO;

import java.util.List;

import lombok.Data;

@Data
public class DietRecordDTO {
    private MealDTO breakfast;
    private MealDTO lunch;
    private MealDTO dinner;
    private List<FoodItemDTO> snacks;
    private List<FoodItemDTO> fruits;

    public MealDTO getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(MealDTO breakfast) {
        this.breakfast = breakfast;
    }

    public MealDTO getLunch() {
        return lunch;
    }

    public void setLunch(MealDTO lunch) {
        this.lunch = lunch;
    }

    public MealDTO getDinner() {
        return dinner;
    }

    public void setDinner(MealDTO dinner) {
        this.dinner = dinner;
    }

    public List<FoodItemDTO> getSnacks() {
        return snacks;
    }

    public void setSnacks(List<FoodItemDTO> snacks) {
        this.snacks = snacks;
    }

    public List<FoodItemDTO> getFruits() {
        return fruits;
    }

    public void setFruits(List<FoodItemDTO> fruits) {
        this.fruits = fruits;
    }
}