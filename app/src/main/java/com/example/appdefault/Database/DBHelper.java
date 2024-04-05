package com.example.appdefault.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_info";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_PROFILE = "profile_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_TDEE = "tdee"; // Thêm cột TDEE
    public static final String COLUMN_PROTEIN_OF_DAY = "protein_of_day"; // Thêm cột protein
    public static final String COLUMN_CARBOHYDRATE_OF_DAY = "carbohydrate_of_day"; // Thêm cột carbohydrate
    public static final String COLUMN_FAT_OF_DAY = "fat_of_day"; // Thêm cột fat

    // Hằng số cho bảng user_table
    public static final String TABLE_USER = "user_table";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng và cột cho bảng thông tin cá nhân
        String createProfileTableQuery = "CREATE TABLE " + TABLE_PROFILE+ " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_AGE + " INTEGER,"
                + COLUMN_HEIGHT + " REAL,"
                + COLUMN_WEIGHT + " REAL,"
                + COLUMN_TDEE + " REAL,"
                + COLUMN_PROTEIN_OF_DAY + " REAL," // Thêm cột protein
                + COLUMN_CARBOHYDRATE_OF_DAY + " REAL," // Thêm cột carbohydrate
                + COLUMN_FAT_OF_DAY + " REAL)"; // Thêm cột fat
        db.execSQL(createProfileTableQuery);

        // Tạo bảng và cột cho bảng người dùng (tên đăng nhập và mật khẩu)
        String createUserTableQuery = "CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUserTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý nâng cấp cơ sở dữ liệu nếu cần
    }
    public void updateProfile(String name, String age, String height, String weight, String tdee, String proteinOfDay, String carbohydrateOfDay, String fatOfDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_TDEE, tdee);
        values.put(COLUMN_PROTEIN_OF_DAY, proteinOfDay);
        values.put(COLUMN_CARBOHYDRATE_OF_DAY, carbohydrateOfDay); // Lưu giá trị carbohydrate of day
        values.put(COLUMN_FAT_OF_DAY, fatOfDay); // Lưu giá trị fat of day

        // Cập nhật dữ liệu trong bảng profile_table
        db.update(TABLE_PROFILE, values, null, null);
        db.close(); // Đóng kết nối CSDL
    }

    public void saveProfile(String name, String age, String height, String weight, String tdee, String proteinOfDay, String carbohydrateOfDay, String fatOfDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_TDEE, tdee);
        values.put(COLUMN_PROTEIN_OF_DAY, proteinOfDay);
        values.put(COLUMN_CARBOHYDRATE_OF_DAY, carbohydrateOfDay);
        values.put(COLUMN_FAT_OF_DAY, fatOfDay);

        // Chèn dữ liệu vào bảng profile_table
        db.insert(TABLE_PROFILE, null, values);
        db.close();
    }


    public Cursor getProfileData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_NAME, COLUMN_AGE, COLUMN_HEIGHT, COLUMN_WEIGHT, COLUMN_TDEE, COLUMN_PROTEIN_OF_DAY, COLUMN_CARBOHYDRATE_OF_DAY, COLUMN_FAT_OF_DAY}; // Thêm COLUMN_CARBOHYDRATE_OF_DAY và COLUMN_FAT_OF_DAY vào projection
        Cursor cursor = db.query(TABLE_PROFILE, projection, null, null, null, null, null);
        return cursor;
    }


    public void deleteProfile() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Xóa dữ liệu trong bảng profile_table
        db.delete(TABLE_PROFILE, null, null);
        db.close(); // Đóng kết nối CSDL
    }

    // Trong lớp DBHelper
    public boolean hasProfileData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PROFILE;
        Cursor cursor = db.rawQuery(query, null);
        boolean hasData = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
        }
        return hasData;
    }

    public boolean isUsernameAvailable(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        Log.d("DBHelper", "Number of rows in cursor: " + cursor.getCount());

        boolean isAvailable = (cursor.getCount() == 0);
        cursor.close();
        db.close();
        return isAvailable;
    }




}
