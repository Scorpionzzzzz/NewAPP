package com.example.appdefault.FoodAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdefault.Activity.ChiTietThucAnActivity;
import com.example.appdefault.Activity.MainActivity;
import com.example.appdefault.Database.MealDBHelper;
import com.example.appdefault.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<FoodItem> foodItems;
    private Context context;
    private MealDBHelper mealDBHelper;
    public FoodAdapter(List<FoodItem> foodItems, Context context) {
        this.foodItems = foodItems;
        this.context = context;
        mealDBHelper = new MealDBHelper(context);
    }
    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
        notifyDataSetChanged(); // Thông báo cho adapter biết rằng dữ liệu đã thay đổi
    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem foodItem = foodItems.get(position);
        holder.textViewFoodName.setText(foodItem.getFoodName());
        holder.textViewCalories.setText("100g - " + String.valueOf(foodItem.getCaloriesValue()) +"calories");
        // Các set dữ liệu khác...

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang FoodDetailActivity và gửi dữ liệu thực phẩm
                Intent intent = new Intent(context, ChiTietThucAnActivity.class);
                intent.putExtra("food_item", foodItem);
                context.startActivity(intent);
            }
        });
        // Trong onBindViewHolder của adapter
        holder.foodOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.meal_options_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_bua_sang:
                                // Thêm món ăn vào bữa sáng
                                mealDBHelper.addFoodToMeal(foodItem.getFoodName(), "Bữa sáng");
                                break;
                            case R.id.menu_bua_trua:
                                // Thêm món ăn vào bữa trưa
                                mealDBHelper.addFoodToMeal(foodItem.getFoodName(), "Bữa trưa");
                                break;
                            case R.id.menu_bua_toi:
                                // Thêm món ăn vào bữa tối
                                mealDBHelper.addFoodToMeal(foodItem.getFoodName(), "Bữa tối");
                                break;
                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewFoodName;
        private TextView textViewCalories;
        private FloatingActionButton foodOptionsButton;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFoodName = itemView.findViewById(R.id.textViewFoodName);
            textViewCalories = itemView.findViewById(R.id.textViewCalories);
            foodOptionsButton = itemView.findViewById(R.id.foodOptionsButton);
            itemView.setOnClickListener(this);
        }

        public void bind(FoodItem foodItem) {
            textViewFoodName.setText(foodItem.getFoodName());
            textViewCalories.setText(String.valueOf(foodItem.getCaloriesValue()));
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                FoodItem foodItem = foodItems.get(position);
                // Chuyển sang trang chi tiết và truyền dữ liệu thực phẩm
                Intent intent = new Intent(context, ChiTietThucAnActivity.class);
                intent.putExtra("food_item",  foodItem);
                context.startActivity(intent);
            }
        }
    }
}
