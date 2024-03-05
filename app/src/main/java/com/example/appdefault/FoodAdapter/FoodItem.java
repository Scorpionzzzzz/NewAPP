// FoodItem.java
package com.example.appdefault.FoodAdapter;

public class FoodItem {
    private int foodId;
    private String foodName;
    private double proteinValue;
    private double carbohydratesValue;
    private double fatValue;
    private double vitaminCValue;
    private double calciumValue;
    private int caloriesValue;

    public FoodItem(int foodId, String foodName, double proteinValue, double carbohydratesValue, double fatValue, double vitaminCValue, double calciumValue, int caloriesValue) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.proteinValue = proteinValue;
        this.carbohydratesValue = carbohydratesValue;
        this.fatValue = fatValue;
        this.vitaminCValue = vitaminCValue;
        this.calciumValue = calciumValue;
        this.caloriesValue = caloriesValue;
    }
    public String getFoodName() {
        return foodName;
    }
    public int getCaloriesValue() {
        return caloriesValue;
    }

}
