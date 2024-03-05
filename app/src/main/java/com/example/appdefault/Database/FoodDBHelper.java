package com.example.appdefault.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appdefault.FoodAdapter.FoodItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "nutrition_database.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng cho loại dinh dưỡng
    public static final String TABLE_NUTRIENT_TYPE = "nutrient_type";
    public static final String COLUMN_NUTRIENT_TYPE_ID = "_id";
    public static final String COLUMN_NUTRIENT_TYPE_NAME = "name";

    // Tên bảng cho thực phẩm
    public static final String TABLE_FOOD = "food";
    public static final String COLUMN_FOOD_ID = "_id";
    public static final String COLUMN_FOOD_NAME = "name";
    public static final String COLUMN_FOOD_NUTRIENT_TYPE_ID = "nutrient_type_id";  // Khóa ngoại
    public static final String COLUMN_PROTEIN = "protein";
    public static final String COLUMN_CARBOHYDRATES = "carbohydrates";
    public static final String COLUMN_FAT = "fat";
    public static final String COLUMN_VITAMIN_C = "vitamin_C";
    public static final String COLUMN_CALCIUM = "calcium";
    public static final String COLUMN_CALORIES = "calories";  // Cột mới

    public FoodDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng cho loại dinh dưỡng
        String createNutrientTypeTableQuery = "CREATE TABLE " + TABLE_NUTRIENT_TYPE + " ("
                + COLUMN_NUTRIENT_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NUTRIENT_TYPE_NAME + " TEXT)";
        db.execSQL(createNutrientTypeTableQuery);

        // Kiểm tra xem bảng food đã tồn tại chưa trước khi tạo
        if (!isTableExists(db, TABLE_FOOD)) {
            // Tạo bảng cho thực phẩm với khóa ngoại là nutrient_type_id
            String createFoodTableQuery = "CREATE TABLE " + TABLE_FOOD + " ("
                    + COLUMN_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_FOOD_NAME + " TEXT,"
                    + COLUMN_PROTEIN + " REAL,"
                    + COLUMN_CARBOHYDRATES + " REAL,"
                    + COLUMN_FAT + " REAL,"
                    + COLUMN_VITAMIN_C + " REAL,"
                    + COLUMN_CALCIUM + " REAL,"
                    + COLUMN_CALORIES + " INTEGER)";
            db.execSQL(createFoodTableQuery);
        }
    }

    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
        boolean tableExists = cursor.getCount() > 0;
        cursor.close();
        return tableExists;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý nâng cấp cơ sở dữ liệu nếu cần
    }
    public List<FoodItem> searchFoodByName(String query, String[] selectionArgs) {
        List<FoodItem> searchResults = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                // Lấy dữ liệu từ Cursor
                int foodId = cursor.getInt(cursor.getColumnIndex(COLUMN_FOOD_ID));
                String foodName = cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_NAME));
                double proteinValue = cursor.getDouble(cursor.getColumnIndex(COLUMN_PROTEIN));
                double carbohydratesValue = cursor.getDouble(cursor.getColumnIndex(COLUMN_CARBOHYDRATES));
                double fatValue = cursor.getDouble(cursor.getColumnIndex(COLUMN_FAT));
                double vitaminCValue = cursor.getDouble(cursor.getColumnIndex(COLUMN_VITAMIN_C));
                double calciumValue = cursor.getDouble(cursor.getColumnIndex(COLUMN_CALCIUM));
                int caloriesValue = cursor.getInt(cursor.getColumnIndex(COLUMN_CALORIES));

                FoodItem foodItem = new FoodItem(foodId, foodName, proteinValue, carbohydratesValue, fatValue, vitaminCValue, calciumValue, caloriesValue);

                searchResults.add(foodItem);
            } while (cursor.moveToNext());
        }

        // Đóng cursor và database
        cursor.close();
        db.close();

        return searchResults;
    }


}
