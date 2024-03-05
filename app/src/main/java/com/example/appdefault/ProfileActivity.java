// ProfileActivity.java
package com.example.appdefault;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdefault.Database.DBHelper;
import com.example.appdefault.fragments.HomeFragment;

public class ProfileActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextHeight;
    private EditText editTextWeight;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new DBHelper(this);

        // Ánh xạ các EditText
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);

        // Ánh xạ nút Save và thêm sự kiện Click
        Button saveButton = findViewById(R.id.buttonSaveProfile);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    private void saveProfile() {
        // Lấy thông tin từ các EditText
        String name = editTextName.getText().toString();
        String age = editTextAge.getText().toString();
        String height = editTextHeight.getText().toString();
        String weight = editTextWeight.getText().toString();

        // Kiểm tra xem có dữ liệu nào bị bỏ trống không
        if (name.isEmpty() || age.isEmpty() || height.isEmpty() || weight.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            // Cập nhật hoặc lưu thông tin vào cơ sở dữ liệu
            if (dbHelper.hasProfileData()) {
                // Nếu có thông tin trong database, thực hiện cập nhật
                dbHelper.updateProfile(name, age, height, weight);
            } else {
                // Nếu chưa có thông tin trong database, thực hiện lưu mới
                dbHelper.deleteProfile();
                dbHelper.saveProfile(name, age, height, weight);
            }

            // Hiển thị thông báo hoặc chuyển về màn hình khác nếu cần
            Toast.makeText(ProfileActivity.this, "Thông tin cá nhân đã được cập nhật", Toast.LENGTH_SHORT).show();
            finish(); // Đóng màn hình ProfileActivity
        }
    }


}


