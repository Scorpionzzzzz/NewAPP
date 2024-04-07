package com.example.appdefault.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdefault.Database.DBHelper;
import com.example.appdefault.MyApp;
import com.example.appdefault.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        dbHelper = new DBHelper(this);

        Button buttonLogin = findViewById(R.id.buttonSaveProfile);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // Trong phương thức xử lý đăng nhập của bạn
    private void handleLogin() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        // Thực hiện xác thực đăng nhập với cơ sở dữ liệu
        if (isValidLogin(username, password)) {
            // Xác định người dùng hiện tại và lưu ID của họ
            long userId = getUserIdByUsername(username);
            setCurrentUserId(userId);

            // Đăng nhập thành công, chuyển sang MainActivity
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            startMainActivity();
        } else {
            // Đăng nhập thất bại, hiển thị thông báo hoặc xử lý khác
            Toast.makeText(this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
        }
    }

    // Phương thức để lấy ID của người dùng dựa trên tên đăng nhập
    private long getUserIdByUsername(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DBHelper.COLUMN_ID};
        String selection = DBHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(DBHelper.TABLE_PROFILE, columns, selection, selectionArgs, null, null, null);

        long userId = -1; // Giá trị mặc định nếu không tìm thấy ID

        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID));
            cursor.close();
        }

        db.close();
        return userId;
    }

    // Phương thức để lưu ID của người dùng hiện tại
    private void setCurrentUserId(long userId) {
        // Lưu ID của người dùng hiện tại vào một biến toàn cục hoặc lớp đặc biệt
        // Ví dụ:
        MyApp.setCurrentUserId(userId);
    }


    private boolean isValidLogin(String username, String password) {
        // Kiểm tra thông tin đăng nhập với cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DBHelper.COLUMN_USERNAME, DBHelper.COLUMN_PASSWORD};
        String selection = DBHelper.COLUMN_USERNAME + " = ? AND " + DBHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(DBHelper.TABLE_PROFILE, columns, selection, selectionArgs, null, null, null);

        boolean isValid = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isValid;
    }


    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Đóng LoginActivity sau khi chuyển sang MainActivity
    }
}
