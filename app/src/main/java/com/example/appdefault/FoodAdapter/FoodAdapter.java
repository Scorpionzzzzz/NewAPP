package com.example.appdefault.FoodAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdefault.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<FoodItem> foodList;

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các View hiển thị thông tin thực phẩm (ví dụ: TextViews)
        public TextView textViewName;
        public TextView textViewCalories;

        public FoodViewHolder(View view) {
            super(view);
            // Ánh xạ các View từ layout
            textViewName = view.findViewById(R.id.textViewFoodName);
            textViewCalories = view.findViewById(R.id.textViewCalories);
        }
    }

    public FoodAdapter(List<FoodItem> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);

        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        // Gán dữ liệu từ foodList vào các View trong holder
        FoodItem foodItem = foodList.get(position);
        holder.textViewName.setText(foodItem.getFoodName());
        holder.textViewCalories.setText(String.valueOf(foodItem.getCaloriesValue()));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
    public void setFoodItems(List<FoodItem> foodList) {
        this.foodList = foodList;
        notifyDataSetChanged();
    }



}

