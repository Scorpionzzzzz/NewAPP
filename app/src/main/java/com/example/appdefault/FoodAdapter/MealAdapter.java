package com.example.appdefault.FoodAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appdefault.R;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private Context mContext;
    private List<Meal> mMealList;

    public MealAdapter(Context context, List<Meal> mealList) {
        mContext = context;
        mMealList = mealList;
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MealViewHolder holder, int position) {
        Meal currentMeal = mMealList.get(position);
        holder.nameTextView.setText(currentMeal.getName());
        holder.caloriesTextView.setText("Calories: " + currentMeal.getCalories());
    }

    @Override
    public int getItemCount() {
        return mMealList.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các view trong mỗi item của RecyclerView
        private TextView nameTextView;
        private TextView caloriesTextView;

        public MealViewHolder(View itemView) {
            super(itemView);
            // Ánh xạ các view trong mỗi item
            nameTextView = itemView.findViewById(R.id.textViewFoodName);
            caloriesTextView = itemView.findViewById(R.id.textViewCalories);
        }
        // Các phương thức khác của MealViewHolder (nếu cần)
    }

}
