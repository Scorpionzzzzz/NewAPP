// ProfileActivity.java
package com.example.appdefault.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdefault.Database.DBHelper;
import com.example.appdefault.R;

public class ProfileActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextHeight;
    private EditText editTextWeight;
    private Spinner spinnerActivityLevel;

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
        spinnerActivityLevel = findViewById(R.id.spinnerActivityLevel); // Ánh xạ Spinner

        // Thiết lập Adapter cho Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivityLevel.setAdapter(adapter);

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
            // Lấy mức độ vận động được chọn từ Spinner
            String activityLevel = spinnerActivityLevel.getSelectedItem().toString();

            // Tính toán BMR dựa trên chiều cao, cân nặng và độ tuổi
            double bmr = calculateBMR(Double.parseDouble(weight), Double.parseDouble(height), Integer.parseInt(age));

            // Tính toán TDEE dựa trên BMR và mức độ vận động
            double tdee = calculateTDEE(bmr, activityLevel);
            double protein = calculateProtein(Double.parseDouble(weight),activityLevel);
            double carbohydrates = calculateCarbohydrates(tdee);
            double fats = calculateFats(tdee);

            // Lấy bội số cho protein, carbohydrate và fat từ các Spinner


            // Cập nhật hoặc lưu thông tin vào cơ sở dữ liệu
            if (dbHelper.hasProfileData()) {
                // Nếu có thông tin trong database, thực hiện cập nhật
                dbHelper.updateProfile(name, age, height, weight, Double.toString(tdee),Double.toString(protein),Double.toString(carbohydrates),Double.toString(fats));
            } else {
                // Nếu chưa có thông tin trong database, thực hiện lưu mới
                dbHelper.deleteProfile();
                dbHelper.saveProfile(name, age, height, weight, Double.toString(tdee),Double.toString(protein),Double.toString(carbohydrates),Double.toString(fats));
            }

            // Hiển thị thông báo hoặc chuyển về màn hình khác nếu cần
            Toast.makeText(ProfileActivity.this, "Thông tin cá nhân đã được cập nhật", Toast.LENGTH_SHORT).show();
            finish(); // Đóng màn hình ProfileActivity
        }
    }


    private double calculateBMR(double weight, double height, int age) {
        // Tính BMR dựa trên công thức
        // BMR = 10W + 6.25H - 5A + 5 (đối với nam)
        // BMR = 10W + 6.25H - 5A - 161 (đối với nữ)
        // Trong đó: W là cân nặng (kg), H là chiều cao (cm), A là tuổi
        // Giả sử là nam
        double bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        return bmr;
    }

    private double calculateTDEE(double bmr, String activityLevel) {
        double tdee = 0;
        // Dựa vào mức độ vận động, tính TDEE theo công thức tương ứng
        switch (activityLevel) {
            case "Không có hoặc ít vận động":
                tdee = 1.2 * bmr;
                break;
            case "Ít vận động (1-3 ngày/tuần)":
                tdee = 1.375 * bmr;
                break;
            case "Vừa phải (3-5 ngày/tuần)":
                tdee = 1.55 * bmr;
                break;
            case "Năng động (6-7 ngày/tuần)":
                tdee = 1.725 * bmr;
                break;
            case "Cực kỳ năng động (Hoạt động nặng, thể dục 2 lần/ngày)":
                tdee = 1.9 * bmr;
                break;
        }
        return tdee;
    }
    private double calculateProtein(double weight,String activityLevel) {
        double protein = 0;

        // Dựa vào bội số protein được chọn, tính lượng protein cần nạp mỗi ngày
        switch (activityLevel) {
            case "Không có hoặc ít vận động":
                protein = 0.5 * weight; // 0.5 gram protein / pound cơ thể
                break;
            case "Nhẹ (1-3 ngày/tuần)":
                protein = 0.75 * weight; // 0.75 gram protein / pound cơ thể
                break;
            case "Vừa phải (3-5 ngày/tuần)":
                protein = 1.0 * weight; // 1 gram protein / pound cơ thể
                break;
            case "Năng động (6-7 ngày/tuần)":
                protein = 1.25 * weight; // 1.25 gram protein / pound cơ thể
                break;
            case "Cực kỳ năng động (Hoạt động nặng, thể dục 2 lần/ngày)":
                protein = 1.5 * weight; // 1.5 gram protein / pound cơ thể
                break;
        }
        return protein;
    }
    private double calculateCarbohydrates(double tdee) {
        // Lượng carbohydrates chiếm khoảng 45-65% tổng lượng calo
        // Sử dụng giá trị trung bình là 50%
        // 1 gram carbohydrates = 4 calo
        double carbohydratesCalories = 0.5 * tdee;
        return carbohydratesCalories / 4; // Chuyển đổi từ calo sang gram
    }

    private double calculateFats(double tdee) {
        // Lượng fats chiếm khoảng 20-35% tổng lượng calo
        // Sử dụng giá trị trung bình là 30%
        // 1 gram fats = 9 calo
        double fatsCalories = 0.3 * tdee;
        double fatsInGrams = fatsCalories / 9; // Chuyển đổi từ calo sang gram
        return Math.round(fatsInGrams * 100.0) / 100.0; // Làm tròn đến số thập phân thứ hai
    }

    public double calculateWaterIntake(double weightInKg) {
        // Ước lượng lượng nước cần uống trong ngày
        // Dựa trên trọng lượng cơ thể (kg)
        // Uống khoảng 30-35ml nước cho mỗi kg trọng lượng cơ thể

        double lowerBound = 30; // Giới hạn dưới (ml/kg)
        double upperBound = 35; // Giới hạn trên (ml/kg)

        // Tính toán lượng nước cần uống
        double waterIntakeLower = weightInKg * lowerBound;
        double waterIntakeUpper = weightInKg * upperBound;

        // Trả về một phạm vi nước cần uống
        // có thể từ giới hạn dưới đến giới hạn trên
        return (waterIntakeLower + waterIntakeUpper) / 2;
    }





}


