package com.example.demo.diet_record_pool.DTO;

import java.util.List;

public class MealDTO {
    private String time;
    private List<FoodItemDTO> items;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<FoodItemDTO> getItems() {
        return items;
    }

    public void setItems(List<FoodItemDTO> items) {
        this.items = items;
    }
}
