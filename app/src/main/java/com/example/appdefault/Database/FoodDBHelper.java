package com.example.appdefault.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appdefault.FoodAdapter.FoodItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
    private Context context;


    public FoodDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

            // Sao chép dữ liệu từ tệp trong thư mục assets
            copyDataFromAssetsToDatabase(db);
            copyDataFromJsonToDatabase();
        }
    }
    private void copyDataFromJsonToDatabase() {
        try {
            // Mở tệp JSON từ thư mục assets
            InputStream inputStream = context.getAssets().open("nutrition_data.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            // Parse JSON và chèn dữ liệu vào cơ sở dữ liệu
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String foodName = jsonObject.getString("name");
                double proteinValue = jsonObject.getDouble("protein");
                double carbohydratesValue = jsonObject.getDouble("carbohydrates");
                double fatValue = jsonObject.getDouble("fat");
                double vitaminCValue = jsonObject.getDouble("vitamin_C");
                double calciumValue = jsonObject.getDouble("calcium");
                int caloriesValue = jsonObject.getInt("calories");

                // Thực hiện thêm dữ liệu vào cơ sở dữ liệu
                // ...

            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    private void copyDataFromAssetsToDatabase(SQLiteDatabase db) {
        try {
            InputStream inputStream = context.getAssets().open("nutrition_data.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            // Parse JSON và chèn dữ liệu vào cơ sở dữ liệu
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String foodName = jsonObject.getString("name");
                double proteinValue = jsonObject.getDouble("protein");
                double carbohydratesValue = jsonObject.getDouble("carbohydrates");
                double fatValue = jsonObject.getDouble("fat");
                double vitaminCValue = jsonObject.getDouble("vitamin_C");
                double calciumValue = jsonObject.getDouble("calcium");
                int caloriesValue = jsonObject.getInt("calories");

                String insertQuery = "INSERT INTO " + TABLE_FOOD + " (" +
                        COLUMN_FOOD_NAME + ", " +
                        COLUMN_PROTEIN + ", " +
                        COLUMN_CARBOHYDRATES + ", " +
                        COLUMN_FAT + ", " +
                        COLUMN_VITAMIN_C + ", " +
                        COLUMN_CALCIUM + ", " +
                        COLUMN_CALORIES + ") VALUES (" +
                        "'" + foodName + "', " +
                        proteinValue + ", " +
                        carbohydratesValue + ", " +
                        fatValue + ", " +
                        vitaminCValue + ", " +
                        calciumValue + ", " +
                        caloriesValue + ")";
                db.execSQL(insertQuery);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
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
    public List<FoodItem> getAllFoodItems(String query) {
        List<FoodItem> foodItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int foodId = cursor.getInt(cursor.getColumnIndex(COLUMN_FOOD_ID));
                String foodName = cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_NAME));
                // Lấy các trường dữ liệu khác của món ăn tương ứng
                double protein = cursor.getDouble(cursor.getColumnIndex(COLUMN_PROTEIN));
                double carbohydrates = cursor.getDouble(cursor.getColumnIndex(COLUMN_CARBOHYDRATES));
                double fat = cursor.getDouble(cursor.getColumnIndex(COLUMN_FAT));
                double vitaminC = cursor.getDouble(cursor.getColumnIndex(COLUMN_VITAMIN_C));
                double calcium = cursor.getDouble(cursor.getColumnIndex(COLUMN_CALCIUM));
                int calories = cursor.getInt(cursor.getColumnIndex(COLUMN_CALORIES));
                // Tạo một đối tượng FoodItem mới
                FoodItem foodItem = new FoodItem(foodId, foodName, protein, carbohydrates, fat, vitaminC, calcium, calories);
                // Thêm đối tượng FoodItem vào danh sách
                foodItems.add(foodItem);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return foodItems;
    }
}
