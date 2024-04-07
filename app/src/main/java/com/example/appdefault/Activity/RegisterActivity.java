// RegisterActivity.java
package com.example.appdefault.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdefault.Database.DBHelper;
import com.example.appdefault.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextNewUsername;
    private EditText editTextNewPassword;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNewUsername = findViewById(R.id.editTextNewUsername);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);

        dbHelper = new DBHelper(this);

        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRegister();
            }
        });
    }

    private void handleRegister() {
        String newUsername = editTextNewUsername.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();

        // Kiểm tra mật khẩu có đủ dài không
        if (newPassword.length() <= 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 kí tự.", Toast.LENGTH_SHORT).show();
            return; // Kết thúc phương thức nếu mật khẩu không đủ dài
        }

        // Kiểm tra xem tên đăng nhập đã tồn tại chưa
        if (isUsernameExist(newUsername)) {
            Toast.makeText(this, "Tên đăng nhập đã tồn tại.", Toast.LENGTH_SHORT).show();
        } else {
            // Thêm thông tin người dùng mới vào bảng user_table
            saveUser(newUsername, newPassword);
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            startLoginActivity();
        }
    }


    private boolean isUsernameExist(String username) {
        // Thực hiện kiểm tra xem tên đăng nhập đã tồn tại trong cơ sở dữ liệu chưa
        // Bạn cần thay thế bằng xử lý thực tế
        return !dbHelper.isUsernameAvailable(username);
    }

    private void saveUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USERNAME, username);
        values.put(DBHelper.COLUMN_PASSWORD, password);

        long newRowId = db.insert(DBHelper.TABLE_PROFILE, null, values);

        // Kiểm tra xem việc thêm dữ liệu có thành công hay không
        if (newRowId == -1) {
            Toast.makeText(this, "Error saving user", Toast.LENGTH_SHORT).show();
        }
    }


    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Đóng RegisterActivity sau khi chuyển sang LoginActivity
    }
}
