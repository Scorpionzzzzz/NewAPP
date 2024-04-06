package com.example.appdefault.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.appdefault.Database.DBHelper;
import com.example.appdefault.FoodAdapter.Meal;
import com.example.appdefault.R;
import com.example.appdefault.FoodAdapter.MealAdapter;
import com.example.appdefault.Database.MealDBHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeFragment extends Fragment {
    private MealAdapter mBreakfastAdapter;
    private MealAdapter mLunchAdapter;
    private MealAdapter mDinnerAdapter;
    private MealDBHelper mMealDBHelper;
    private TextView textViewCaloOfDay;
    private TextView textViewCalodanap;
    private TextView textViewProtein;
    private TextView textViewCarbohydrates;
    private TextView textViewFat;
    private TextView textViewProteinOfDay;
    private TextView textViewCarbohydratesOfDay;
    private TextView textViewFatOfDay;
    private LinearProgressIndicator linearProgressIndicatorProtein;
    private LinearProgressIndicator linearProgressIndicatorCarbohydrate;
    private LinearProgressIndicator linearProgressIndicatorFat;
    private DBHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        dbHelper = new DBHelper(requireContext());
        mMealDBHelper = new MealDBHelper(requireContext());

        //thanh tien do
        double mealCalories = getTotalCaloriesConsumed(); // Hàm này tính tổng số calo từ thực đơn
        double totalCaloriesOfDay = getTDEEFromDatabase(); // Hàm này tính tổng số calo của ngày

        int percentage = (int) ((mealCalories / totalCaloriesOfDay) * 100);


        // Đặt phần trăm vào CircularProgressIndicator
        CircularProgressIndicator circularProgressIndicator = view.findViewById(R.id.circularProgressIndicator);
        circularProgressIndicator.setProgress(percentage);

        linearProgressIndicatorProtein = view.findViewById(R.id.linearProgressIndicator);
        linearProgressIndicatorCarbohydrate = view.findViewById(R.id.linearProgressIndicator3);
        linearProgressIndicatorFat = view.findViewById(R.id.linearProgressIndicator2);





        //cac view item
        RecyclerView recyclerViewBreakfast = view.findViewById(R.id.recyclerViewBreakfast);
        RecyclerView recyclerViewLunch = view.findViewById(R.id.recyclerViewLunch);
        RecyclerView recyclerViewDinner = view.findViewById(R.id.recyclerViewDinner);


        // Tạo LayoutManager riêng cho mỗi RecyclerView
        LinearLayoutManager layoutManagerBreakfast = new LinearLayoutManager(requireContext());
        LinearLayoutManager layoutManagerLunch = new LinearLayoutManager(requireContext());
        LinearLayoutManager layoutManagerDinner = new LinearLayoutManager(requireContext());

        // Thiết lập LayoutManager cho từng RecyclerView
        recyclerViewBreakfast.setLayoutManager(layoutManagerBreakfast);
        recyclerViewLunch.setLayoutManager(layoutManagerLunch);
        recyclerViewDinner.setLayoutManager(layoutManagerDinner);

        // Load meals for breakfast
        List<Meal> breakfastList = mMealDBHelper.getMealsByType("Bữa sáng");
        mBreakfastAdapter = new MealAdapter(requireContext(), breakfastList);
        recyclerViewBreakfast.setAdapter((RecyclerView.Adapter) mBreakfastAdapter);

        // Load meals for lunch
        List<Meal> lunchList = mMealDBHelper.getMealsByType("Bữa trưa");
        mLunchAdapter = new MealAdapter(requireContext(), lunchList);
        recyclerViewLunch.setAdapter((RecyclerView.Adapter) mLunchAdapter);

        // Load meals for dinner
        List<Meal> dinnerList = mMealDBHelper.getMealsByType("Bữa tối");
        mDinnerAdapter = new MealAdapter(requireContext(), dinnerList);
        recyclerViewDinner.setAdapter((RecyclerView.Adapter) mDinnerAdapter);

        //chuc nang tu day
        textViewCaloOfDay = view.findViewById(R.id.textviewCaloOfDay);
        textViewCalodanap = view.findViewById(R.id.textViewCalodanap);
        textViewProtein = view.findViewById(R.id.textViewProtein);
        textViewCarbohydrates = view.findViewById(R.id.textViewCarbohydrates);
        textViewFat = view.findViewById(R.id.textViewFat);
        textViewProteinOfDay = view.findViewById(R.id.textViewProteinOfDay);
        textViewCarbohydratesOfDay = view.findViewById(R.id.textViewCarbsOfDay);
        textViewFatOfDay = view.findViewById(R.id.textViewFatofDay);



        // Cacs so lieu
        int tdee = getTDEEFromDatabase();

        double proteinOfDay = getProteinFromDatabase();
        double carbohydratesOfDay = getCarbohydratesFromDatabase();
        double fatOfDay = getFatFromDatabase();
        int totalCalories = getTotalCaloriesConsumed();
        double totalProtein = getTotalProteinConsumed();
        double totalCarbohydrates = getTotalCarbohydratesConsumed();
        double totalFats = getTotalFatsConsumed();
        double proteinProgress = (totalProtein/proteinOfDay)*100;
        double carbohydrateProgress = (totalCarbohydrates/carbohydratesOfDay)*100;
        double fatProgress = (totalFats/fatOfDay)*100;
        textViewCaloOfDay.setText("of " + tdee + " kcal");
        textViewProteinOfDay.setText("of " + proteinOfDay);
        textViewCarbohydratesOfDay.setText("of " + carbohydratesOfDay);
        textViewFatOfDay.setText("of " + fatOfDay);
        textViewProtein.setText(String.valueOf(totalProtein));
        textViewCarbohydrates.setText(String.valueOf(totalCarbohydrates));
        textViewFat.setText(String.valueOf(totalFats));
        textViewCalodanap.setText(String.valueOf(totalCalories));
        linearProgressIndicatorProtein.setProgress((int) proteinProgress);
        linearProgressIndicatorCarbohydrate.setProgress((int) carbohydrateProgress);
        linearProgressIndicatorFat.setProgress((int) fatProgress);

        MaterialButton buttonResetBreakfast = view.findViewById(R.id.buttonResetBreafast);
        MaterialButton buttonResetLunch = view.findViewById(R.id.buttonResetLunch);
        MaterialButton buttonResetDinner = view.findViewById(R.id.buttonResetDinner);
        buttonResetBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức để xóa thực đơn bữa sáng từ adapter
                resetBreakfastMenu();
            }
        });
        buttonResetLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức để xóa thực đơn bữa trưa từ adapter
                resetLunchMenu();
                }
        });
        buttonResetDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức để xóa thực đơn bữa tối từ adapter
                resetDinnerMenu();
            }
            });



        return view;
    }
    private void resetBreakfastMenu() {
        // Xóa thực đơn bữa sáng từ adapter
        deleteBreakfastMenuFromDatabase();
        mBreakfastAdapter.clearMenu();
    }
    private void resetLunchMenu() {
        // Xóa thực đơn bữa trưa từ adapter
        deleteLunchMenuFromDatabase();
        mLunchAdapter.clearMenu();
    }
    private void resetDinnerMenu() {
        // Xóa thực đơn bữa tối từ adapter
        deleteDinnerMenuFromDatabase();
        mDinnerAdapter.clearMenu();
    }
    private void deleteBreakfastMenuFromDatabase() {
        // Thực hiện xóa thực đơn bữa sáng từ cơ sở dữ liệu
        mMealDBHelper.deleteMealsByType("Bữa sáng");
    }
    private void deleteLunchMenuFromDatabase() {
        // Thực hiện xóa thực đơn bữa trưa từ cơ sở dữ liệu
        mMealDBHelper.deleteMealsByType("Bữa trưa");
    }
    private void deleteDinnerMenuFromDatabase() {
        // Thực hiện xóa thực đơn bữa tối từ cơ sở dữ liệu
        mMealDBHelper.deleteMealsByType("Bữa tối");
    }



    private int getTDEEFromDatabase() {
        int tdee = 0;

        // Mở kết nối đến cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Thiết lập các cột bạn muốn truy vấn
        String[] projection = {DBHelper.COLUMN_TDEE};

        // Sử dụng query để thực hiện truy vấn
        Cursor cursor = db.query(DBHelper.TABLE_PROFILE, projection, null, null, null, null, null);

        // Kiểm tra xem có dữ liệu trong cursor không và di chuyển con trỏ đến hàng đầu tiên
        if (cursor != null && cursor.moveToFirst()) {
            // Lấy giá trị TDEE từ cột tương ứng trong bảng
            tdee = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TDEE));

            // Đóng cursor sau khi sử dụng
            cursor.close();
        }

        // Đóng kết nối đến cơ sở dữ liệu
        db.close();

        return tdee;
    }
    private double getProteinFromDatabase() {
        double protein = 0;

        // Mở kết nối đến cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Thiết lập các cột bạn muốn truy vấn
        String[] projection = {DBHelper.COLUMN_PROTEIN_OF_DAY};

        // Sử dụng query để thực hiện truy vấn
        Cursor cursor = db.query(DBHelper.TABLE_PROFILE, projection, null, null, null, null, null);

        // Kiểm tra xem có dữ liệu trong cursor không và di chuyển con trỏ đến hàng đầu tiên
        if (cursor != null && cursor.moveToFirst()) {
            // Lấy giá trị protein từ cột tương ứng trong bảng
            protein = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROTEIN_OF_DAY));

            // Đóng cursor sau khi sử dụng
            cursor.close();
        }

        // Đóng kết nối đến cơ sở dữ liệu
        db.close();

        return protein;
    }
    private double getCarbohydratesFromDatabase() {
        double carbohydrates  = 0;

        // Mở kết nối đến cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Thiết lập các cột bạn muốn truy vấn
        String[] projection = {DBHelper.COLUMN_CARBOHYDRATE_OF_DAY};

        // Sử dụng query để thực hiện truy vấn
        Cursor cursor = db.query(DBHelper.TABLE_PROFILE, projection, null, null, null, null, null);

        // Kiểm tra xem có dữ liệu trong cursor không và di chuyển con trỏ đến hàng đầu tiên
        if (cursor != null && cursor.moveToFirst()) {
            // Lấy giá trị protein từ cột tương ứng trong bảng
            carbohydrates  = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CARBOHYDRATE_OF_DAY));

            // Đóng cursor sau khi sử dụng
            cursor.close();
        }

        // Đóng kết nối đến cơ sở dữ liệu
        db.close();

        return carbohydrates;
    }
    private double getFatFromDatabase() {
        double fat  = 0;

        // Mở kết nối đến cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Thiết lập các cột bạn muốn truy vấn
        String[] projection = {DBHelper.COLUMN_FAT_OF_DAY};

        // Sử dụng query để thực hiện truy vấn
        Cursor cursor = db.query(DBHelper.TABLE_PROFILE, projection, null, null, null, null, null);

        // Kiểm tra xem có dữ liệu trong cursor không và di chuyển con trỏ đến hàng đầu tiên
        if (cursor != null && cursor.moveToFirst()) {
            // Lấy giá trị protein từ cột tương ứng trong bảng
            fat  = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_FAT_OF_DAY));

            // Đóng cursor sau khi sử dụng
            cursor.close();
        }

        // Đóng kết nối đến cơ sở dữ liệu
        db.close();

        return fat;
    }


    public int getTotalCaloriesConsumed() {
        int totalCalories = 0;

        // Truy vấn lượng calo của các món ăn trong mỗi bữa
        List<Meal> breakfastList = mMealDBHelper.getMealsByType("Bữa sáng");
        List<Meal> lunchList = mMealDBHelper.getMealsByType("Bữa trưa");
        List<Meal> dinnerList = mMealDBHelper.getMealsByType("Bữa tối");

        totalCalories += getTotalCaloriesFromMeals(breakfastList);
        totalCalories += getTotalCaloriesFromMeals(lunchList);
        totalCalories += getTotalCaloriesFromMeals(dinnerList);
        totalCalories = Math.round(totalCalories);
        return totalCalories;
    }
    public double getTotalProteinConsumed() {
        double totalProtein = 0;

        // Truy vấn lượng protein của các món ăn trong mỗi bữa
        List<Meal> breakfastList = mMealDBHelper.getMealsByType("Bữa sáng");
        List<Meal> lunchList = mMealDBHelper.getMealsByType("Bữa trưa");
        List<Meal> dinnerList = mMealDBHelper.getMealsByType("Bữa tối");

        totalProtein += getTotalProteinFromMeals(breakfastList);
        totalProtein += getTotalProteinFromMeals(lunchList);
        totalProtein += getTotalProteinFromMeals(dinnerList);
        totalProtein = Math.round(totalProtein*100)/100.0;

        return totalProtein;
    }

    public double getTotalCarbohydratesConsumed() {
        double totalCarbohydrates = 0;

        // Truy vấn lượng carbohydrates của các món ăn trong mỗi bữa
        List<Meal> breakfastList = mMealDBHelper.getMealsByType("Bữa sáng");
        List<Meal> lunchList = mMealDBHelper.getMealsByType("Bữa trưa");
        List<Meal> dinnerList = mMealDBHelper.getMealsByType("Bữa tối");

        totalCarbohydrates += getTotalCarbohydratesFromMeals(breakfastList);
        totalCarbohydrates += getTotalCarbohydratesFromMeals(lunchList);
        totalCarbohydrates += getTotalCarbohydratesFromMeals(dinnerList);
        totalCarbohydrates = Math.round(totalCarbohydrates*100)/100.0;

        return totalCarbohydrates;
    }

    public double getTotalFatsConsumed() {
        double totalFats = 0;

        // Truy vấn lượng fats của các món ăn trong mỗi bữa
        List<Meal> breakfastList = mMealDBHelper.getMealsByType("Bữa sáng");
        List<Meal> lunchList = mMealDBHelper.getMealsByType("Bữa trưa");
        List<Meal> dinnerList = mMealDBHelper.getMealsByType("Bữa tối");

        totalFats += getTotalFatsFromMeals(breakfastList);
        totalFats += getTotalFatsFromMeals(lunchList);
        totalFats += getTotalFatsFromMeals(dinnerList);
        totalFats = Math.round(totalFats*100)/100.0;
        return totalFats;
    }


    private int getTotalCaloriesFromMeals(List<Meal> meals) {
        int totalCalories = 0;
        for (Meal meal : meals) {
            totalCalories += meal.getCalories();
        }
        totalCalories = Math.round(totalCalories);
        return totalCalories;
    }
    private double getTotalProteinFromMeals(List<Meal> meals) {
        double totalProtein = 0;
        for (Meal meal : meals) {
            totalProtein += meal.getProtein();
        }
        totalProtein = Math.round(totalProtein*100)/100.0;
        return totalProtein;
    }

    private double getTotalCarbohydratesFromMeals(List<Meal> meals) {
        double totalCarbohydrates = 0;
        for (Meal meal : meals) {
            totalCarbohydrates += meal.getCarbohydrates();
        }
        totalCarbohydrates = Math.round(totalCarbohydrates*100)/100.0;
        return totalCarbohydrates;
    }

    private double getTotalFatsFromMeals(List<Meal> meals) {
        double totalFats = 0;
        for (Meal meal : meals) {
            totalFats += meal.getFat();
        }
        totalFats = Math.round(totalFats*100)/100.0;
        return totalFats;
    }


}

