package com.example.appdefault.FoodAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appdefault.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MealAdapter extends ArrayAdapter<Meal> {

    private Context mContext;
    private List<Meal> mMealList;

    public MealAdapter(Context context, List<Meal> mealList) {
        super(context, 0, mealList);
        mContext = context;
        mMealList = mealList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Kiểm tra xem convertView có được sử dụng lại hay không, nếu không thì inflate layout mới
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.meal_item, parent, false);
        }

        // Lấy món ăn ở vị trí hiện tại
        Meal currentMeal = mMealList.get(position);

        // Ánh xạ các view trong layout của món ăn
        TextView nameTextView = convertView.findViewById(R.id.textViewFoodName);
        TextView caloriesTextView = convertView.findViewById(R.id.textViewCalories);


        // Đặt thông tin của món ăn vào các view tương ứng
        nameTextView.setText(currentMeal.getName());
        caloriesTextView.setText("Calories: " + currentMeal.getCalories());

        return convertView;
    }


}
