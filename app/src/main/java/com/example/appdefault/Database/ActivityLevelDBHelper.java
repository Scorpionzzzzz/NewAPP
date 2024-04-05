package com.example.appdefault.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ActivityLevelDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "activity_levels.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    private static final String TABLE_ACTIVITY_LEVEL = "activity_levels";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ACTIVITY_LEVEL = "activity_level";
    private static final String COLUMN_MULTIPLIER_PROTEIN = "multiplier_protein";
    private static final String COLUMN_MULTIPLIER_FAT = "multiplier_fat";
    private static final String COLUMN_MULTIPLIER_CARBOHYDRATE = "multiplier_carbohydrate";

    public ActivityLevelDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng activity_levels
        String CREATE_ACTIVITY_LEVEL_TABLE = "CREATE TABLE " + TABLE_ACTIVITY_LEVEL +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_ACTIVITY_LEVEL + " INTEGER," +
                COLUMN_MULTIPLIER_PROTEIN + " REAL," +
                COLUMN_MULTIPLIER_FAT + " REAL," +
                COLUMN_MULTIPLIER_CARBOHYDRATE + " REAL" +
                ")";
        db.execSQL(CREATE_ACTIVITY_LEVEL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại và tạo lại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY_LEVEL);
        onCreate(db);
    }

    // Thêm một mức độ hoạt động mới
    public void addActivityLevel(int activityLevel, double multiplierProtein, double multiplierFat, double multiplierCarbohydrate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTIVITY_LEVEL, activityLevel);
        values.put(COLUMN_MULTIPLIER_PROTEIN, multiplierProtein);
        values.put(COLUMN_MULTIPLIER_FAT, multiplierFat);
        values.put(COLUMN_MULTIPLIER_CARBOHYDRATE, multiplierCarbohydrate);
        db.insert(TABLE_ACTIVITY_LEVEL, null, values);
        db.close();
    }

    // Đọc mức độ hoạt động từ cơ sở dữ liệu
    public double getProteinMultiplier(int activityLevel) {
        SQLiteDatabase db = this.getReadableDatabase();
        double multiplier = 0;
        Cursor cursor = db.query(TABLE_ACTIVITY_LEVEL, new String[]{COLUMN_MULTIPLIER_PROTEIN},
                COLUMN_ACTIVITY_LEVEL + "=?", new String[]{String.valueOf(activityLevel)},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            multiplier = cursor.getDouble(cursor.getColumnIndex(COLUMN_MULTIPLIER_PROTEIN));
            cursor.close();
        }
        db.close();
        return multiplier;
    }

    // Tương tự cho bội số cho chất béo và carbohydrate
}
