package com.example.appdefault.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appdefault.FoodAdapter.FoodItem;
import com.example.appdefault.FoodAdapter.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_meals.db";
    private static final int DATABASE_VERSION = 1;
    private FoodDBHelper foodDBHelper;

    public MealDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        foodDBHelper = new FoodDBHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE meals (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "calories INTEGER, " +
                "carbohydrates REAL, " +
                "fat REAL, " +
                "protein REAL, " +
                "time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS meals");
        onCreate(db);
    }

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
        } else {
            // Xử lý trường hợp không tìm thấy món ăn
        }
    }

    private void insertMeal(String name, int calories, double carbohydrates, double fat, double protein, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
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
    public List<Meal> getMealsByType(String mealType) {
        List<Meal> mealList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"name", "calories", "carbohydrates", "fat", "protein"};
        String selection = "time = ?";
        String[] selectionArgs = {mealType};

        Cursor cursor = db.query("meals", columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int calories = cursor.getInt(cursor.getColumnIndex("calories"));
                double carbohydrates = cursor.getDouble(cursor.getColumnIndex("carbohydrates"));
                double fat = cursor.getDouble(cursor.getColumnIndex("fat"));
                double protein = cursor.getDouble(cursor.getColumnIndex("protein"));

                Meal meal = new Meal(name, calories, carbohydrates, fat, protein, null); // Truyền null cho tham số time
                mealList.add(meal);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();

        return mealList;
    }

}
