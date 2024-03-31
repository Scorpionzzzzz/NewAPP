package com.example.appdefault.FoodAdapter;

import java.util.Date;

public class Meal {
    private String name;
    private int calories;
    private double carbohydrates;
    private double fat;
    private double protein;
    private Date time;

    public Meal(String name, int calories, double carbohydrates, double fat, double protein, Date time) {
        this.name = name;
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.protein = protein;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    // Ghi đè phương thức toString()
    @Override
    public String toString() {
        return "Meal{" +
                "name='" + name + '\'' +
                ", calories=" + calories +
                ", carbohydrates=" + carbohydrates +
                ", fat=" + fat +
                ", protein=" + protein +
                ", time=" + time +
                '}';
    }
}
