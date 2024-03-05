package com.example.appdefault.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appdefault.Database.DBHelper;
import com.example.appdefault.DietPlanActivity;
import com.example.appdefault.MainActivity;
import com.example.appdefault.ProfileActivity;
import com.example.appdefault.R;

// HomeFragment.java

public class HomeFragment extends Fragment {

    private boolean isDietMenuAdded = false;
    public static final String KEY_NAME = "name";
    public static final String KEY_AGE = "age";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_WEIGHT = "weight";
    private TextView textViewBMI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        DBHelper dbHelper = new DBHelper(getActivity());
        Cursor cursor = dbHelper.getProfileData();

        // Ánh xạ các TextView trong layout fragment_home.xml
        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewAge = view.findViewById(R.id.textViewAge);
        TextView textViewHeight = view.findViewById(R.id.textViewHeight);
        TextView textViewWeight = view.findViewById(R.id.textViewWeight);
        textViewBMI = view.findViewById(R.id.textViewBMI); // Thêm TextView mới cho chỉ số BMI

        // Lấy thông tin từ Bundle
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME));
            int age = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_AGE));
            float height = cursor.getFloat(cursor.getColumnIndex(DBHelper.COLUMN_HEIGHT));
            float weight = cursor.getFloat(cursor.getColumnIndex(DBHelper.COLUMN_WEIGHT));

            // Đặt thông tin vào các TextView
            textViewName.setText("Tên: " + name);
            textViewAge.setText("Tuổi: " + age);
            textViewHeight.setText("Chiều cao: " + height +"cm");
            textViewWeight.setText("Cân nặng: " + weight+"kg");
            calculateAndDisplayBMI(height, weight);
            cursor.close();
        }


        Button buttonDietPlan = view.findViewById(R.id.buttonDietPlan);
        ImageButton buttonProfile = view.findViewById(R.id.buttonProfile);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
            
        });

        buttonDietPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DietPlanActivity.class);
                startActivity(intent);
            }
        });

        Intent intentt = new Intent();

        return view;
    }
    private void calculateAndDisplayBMI(float height, float weight) {
        // Tính chỉ số BMI: BMI = weight(kg) / (height(m) * height(m))
        float heightInMeters = height / 100; // Chuyển chiều cao từ cm sang m
        float bmi = weight / (heightInMeters * heightInMeters);

        // Hiển thị chỉ số BMI
        Log.d("HomeFragment", "Chỉ số BMI: " + bmi);
        textViewBMI.setText("BMI: " + String.format("%.2f", bmi));
    }






}


