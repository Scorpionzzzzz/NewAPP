package com.example.appdefault.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appdefault.Activity.ProfileActivity;
import com.example.appdefault.Caculator;
import com.example.appdefault.Database.DBHelper;
import com.example.appdefault.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CaNhanFragment extends Fragment {

    private TextView textHeight;
    private TextView textWeight; // Thêm TextView cho cân nặng
    private TextView textBMI;
    private DBHelper dbHelper;
    private TextView textWaterOfDay;
    private TextView textViewLich;


    private Handler handler;

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            // Cập nhật dữ liệu trong fragment ở đây
            updateData();
            // Lập lịch để chạy lại Runnable sau 1 giây
            handler.postDelayed(this, 1000);
        }
    };

    public CaNhanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ca_nhan, container, false);

        // Ánh xạ TextView cho chiều cao và cân nặng

        textHeight = rootView.findViewById(R.id.textHeight);
        textWeight = rootView.findViewById(R.id.textWeight);
        textBMI = rootView.findViewById(R.id.textBMI);
        textWaterOfDay = rootView.findViewById(R.id.textViewWaterOfDay);
        textViewLich = rootView.findViewById(R.id.textViewLich);
        Button buttonSuaThongTin = rootView.findViewById(R.id.Buttonsuathongtin);

        // Khởi tạo Handler
        handler = new Handler(Looper.getMainLooper());

        //time
        updateDateTime();

        buttonSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang ProfileActivity
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                // Khởi chạy ProfileActivity
                startActivity(intent);
            }
        });

        // Khởi tạo DBHelper
        dbHelper = new DBHelper(getActivity());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Bắt đầu cập nhật dữ liệu khi fragment được hiển thị
        handler.post(updateRunnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Dừng cập nhật dữ liệu khi fragment không còn hiển thị
        handler.removeCallbacks(updateRunnable);
    }

    private void updateDateTime() {
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'tháng' MM '-' HH:mm", Locale.getDefault());
        String dateTimeString = sdf.format(currentTime);
        textViewLich.setText(dateTimeString);
    }

    private void updateData() {
        // Lấy dữ liệu từ cơ sở dữ liệu và cập nhật giao diện người dùng
        Cursor cursor = dbHelper.getProfileData();
        if (cursor != null && cursor.moveToFirst()) {
            // Lấy thông tin chiều cao và cân nặng từ Cursor
            int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
            String name = cursor.getString(nameIndex);

            int heightIndex = cursor.getColumnIndex(DBHelper.COLUMN_HEIGHT);
            int weightIndex = cursor.getColumnIndex(DBHelper.COLUMN_WEIGHT);
            int height = cursor.getInt(heightIndex);
            int weight = cursor.getInt(weightIndex);
            double bmi = Caculator.calculateBMI(weight, height);

            // Đặt thông tin vào TextView
            textHeight.setText(height + " cm");
            textWeight.setText(weight + " kg");
            textBMI.setText(String.format("%.1f", bmi));

            // Đóng Cursor sau khi sử dụng
            cursor.close();
        }
        // Tính và hiển thị lượng nước cần uống
        int waterIntake = calculateWaterIntake();
        textWaterOfDay.setText(String.valueOf(waterIntake + " ml"));
    }

    private int calculateWaterIntake() {
        // Assume the average water intake per kg of body weight
        double averageWaterIntake = (30 + 35) / 2.0; // Lấy trung bình của 30 và 35 ml/kg

        // Lấy trọng lượng từ cơ sở dữ liệu
        Cursor cursor = dbHelper.getProfileData();
        if (cursor != null && cursor.moveToFirst()) {
            double weight = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_WEIGHT));
            cursor.close();
            // Tính lượng nước cần uống trong ngày dựa trên trọng lượng (kg)
            return (int) (weight * averageWaterIntake);
        }
        return 0;
    }
}
