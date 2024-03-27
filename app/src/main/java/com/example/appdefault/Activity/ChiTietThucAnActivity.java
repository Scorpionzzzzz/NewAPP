package com.example.appdefault.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdefault.FoodAdapter.FoodItem;
import com.example.appdefault.R;

public class ChiTietThucAnActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_thuc_an);

        // Lấy dữ liệu thực phẩm từ intent và hiển thị thông tin chi tiết
        FoodItem foodItem = getIntent().getParcelableExtra("food_item");
        if (foodItem != null) {
            // Hiển thị thông tin chi tiết của thực phẩm
            TextView textViewFoodName = findViewById(R.id.textViewFoodName);
            TextView textViewCaloriesValue = findViewById(R.id.textViewCaloriesValue);
            TextView textViewProteinValue = findViewById(R.id.textViewProteinValue);
            TextView textViewFatValue = findViewById(R.id.textViewFatValue);
            TextView textViewCarbohydratesValue = findViewById(R.id.textViewCarbohydratesValue);

            textViewFoodName.setText(foodItem.getFoodName());
            textViewCaloriesValue.setText(String.valueOf(foodItem.getCaloriesValue()));
            textViewProteinValue.setText(String.valueOf(foodItem.getProteinValue()));
            textViewFatValue.setText(String.valueOf(foodItem.getFatValue()));
            textViewCarbohydratesValue.setText(String.valueOf(foodItem.getCarbohydratesValue()));
        }
    }
}

