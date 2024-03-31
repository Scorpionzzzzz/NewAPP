package com.example.appdefault.FoodAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdefault.Database.FoodDBHelper;

import java.util.List;

public class FoodDataManager {
    private FoodDBHelper foodDBHelper;

    public FoodDataManager(Context context) {
        foodDBHelper = new FoodDBHelper(context);
    }

    // Thêm món ăn vào bữa ăn tương ứng trong cơ sở dữ liệu
    public void addFoodToMeal(String foodName, String time) {
        // Lấy thông tin về món ăn từ FoodDBHelper và chèn vào bảng meals
        List<FoodItem> foodItems = foodDBHelper.searchFoodByName("SELECT * FROM food WHERE name=?", new String[]{foodName});
        if (!foodItems.isEmpty()) {
            FoodItem foodItem = foodItems.get(0);
            String name = foodItem.getFoodName();
            int calories = foodItem.getCaloriesValue();
            double carbohydrates = foodItem.getCarbohydratesValue();
            double fat = foodItem.getFatValue();
            double protein = foodItem.getProteinValue();

            insertMeal(name, calories, carbohydrates, fat, protein, time);
        }
    }

    // Chèn một bữa ăn vào cơ sở dữ liệu
    private void insertMeal(String name, int calories, double carbohydrates, double fat, double protein, String time) {
        SQLiteDatabase db = foodDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("calories", calories);
        values.put("carbohydrates", carbohydrates);
        values.put("fat", fat);
        values.put("protein", protein);
        values.put("time", time);
        db.insert("meals", null, values);
        db.close();
    }

    // Các phương thức khác liên quan đến quản lý dữ liệu món ăn
}
