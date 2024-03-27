package com.example.appdefault.FoodAdapter;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodItem implements Parcelable {
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

    protected FoodItem(Parcel in) {
        foodId = in.readInt();
        foodName = in.readString();
        proteinValue = in.readDouble();
        carbohydratesValue = in.readDouble();
        fatValue = in.readDouble();
        vitaminCValue = in.readDouble();
        calciumValue = in.readDouble();
        caloriesValue = in.readInt();
    }

    public static final Creator<FoodItem> CREATOR = new Creator<FoodItem>() {
        @Override
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };

    public int getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getProteinValue() {
        return proteinValue;
    }

    public double getCarbohydratesValue() {
        return carbohydratesValue;
    }

    public double getFatValue() {
        return fatValue;
    }

    public double getVitaminCValue() {
        return vitaminCValue;
    }

    public double getCalciumValue() {
        return calciumValue;
    }

    public int getCaloriesValue() {
        return caloriesValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(foodId);
        dest.writeString(foodName);
        dest.writeDouble(proteinValue);
        dest.writeDouble(carbohydratesValue);
        dest.writeDouble(fatValue);
        dest.writeDouble(vitaminCValue);
        dest.writeDouble(calciumValue);
        dest.writeInt(caloriesValue);
    }
}
