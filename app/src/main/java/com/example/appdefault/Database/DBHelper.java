package com.example.appdefault.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_info.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_PROFILE = "profile_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_TDEE = "tdee";
    public static final String COLUMN_PROTEIN_OF_DAY = "protein_of_day";
    public static final String COLUMN_CARBOHYDRATE_OF_DAY = "carbohydrate_of_day";
    public static final String COLUMN_FAT_OF_DAY = "fat_of_day";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCombinedTableQuery = "CREATE TABLE " + TABLE_PROFILE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_AGE + " INTEGER,"
                + COLUMN_HEIGHT + " REAL,"
                + COLUMN_WEIGHT + " REAL,"
                + COLUMN_TDEE + " REAL,"
                + COLUMN_PROTEIN_OF_DAY + " REAL,"
                + COLUMN_CARBOHYDRATE_OF_DAY + " REAL,"
                + COLUMN_FAT_OF_DAY + " REAL,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createCombinedTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade if needed
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
        values.put(COLUMN_CARBOHYDRATE_OF_DAY, carbohydrateOfDay);
        values.put(COLUMN_FAT_OF_DAY, fatOfDay);
        db.update(TABLE_PROFILE, values, null, null);
        db.close();
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
        db.insert(TABLE_PROFILE, null, values);
        db.close();
    }

    public Cursor getProfileData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_NAME, COLUMN_AGE, COLUMN_HEIGHT, COLUMN_WEIGHT, COLUMN_TDEE, COLUMN_PROTEIN_OF_DAY, COLUMN_CARBOHYDRATE_OF_DAY, COLUMN_FAT_OF_DAY};
        Cursor cursor = db.query(TABLE_PROFILE, projection, null, null, null, null, null);
        return cursor;
    }

    public void deleteProfile() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROFILE, null, null);
        db.close();
    }

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
        String query = "SELECT * FROM " + TABLE_PROFILE + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean isAvailable = cursor.getCount() == 0;
        cursor.close();
        db.close();
        return isAvailable;
    }
}
