package com.example.appdefault.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdefault.FoodAdapter.FoodAdapter;
import com.example.appdefault.FoodAdapter.FoodItem;
import com.example.appdefault.R;
import com.example.appdefault.Database.FoodDBHelper;


import java.util.List;
import java.util.Locale;

public class ThucAnFragment extends Fragment {

    private EditText editTextSearch;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private FoodDBHelper foodDBHelper;
    private List<FoodItem> allFoodItems; // Danh sách tất cả các món ăn

    public ThucAnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thuc_an, container, false);

        editTextSearch = view.findViewById(R.id.editTextSearch);
        recyclerView = view.findViewById(R.id.recyclerViewRecipes);

        foodDBHelper = new FoodDBHelper(requireContext());

        // Thực hiện truy vấn SQL để lấy tất cả các món ăn
        String query = "SELECT * FROM " + FoodDBHelper.TABLE_FOOD;
        allFoodItems = foodDBHelper.getAllFoodItems(query);

        foodAdapter = new FoodAdapter(allFoodItems, requireContext());
        // Cập nhật Adapter với tất cả các món ăn

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(foodAdapter);

        // Thêm TextWatcher cho EditText để theo dõi thay đổi
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                performSearch(s.toString()); // Gọi hàm tìm kiếm sau khi người dùng đã nhập xong
            }
        });

        return view;
    }

    private void performSearch(String searchTerm) {
        if (searchTerm.isEmpty()) {
            // Nếu chuỗi tìm kiếm trống, hiển thị tất cả các món ăn
            foodAdapter.setFoodItems(allFoodItems);
        } else {
            // Nếu có chuỗi tìm kiếm, thực hiện tìm kiếm và cập nhật Adapter với kết quả
            String lowercaseSearchTerm = searchTerm.toLowerCase(Locale.getDefault());
            String query = "SELECT * FROM " + FoodDBHelper.TABLE_FOOD + " WHERE LOWER(" + FoodDBHelper.COLUMN_FOOD_NAME + ") LIKE ?";
            List<FoodItem> searchResults = foodDBHelper.searchFoodByName(query, new String[]{"%" + lowercaseSearchTerm + "%"});
            foodAdapter.setFoodItems(searchResults);
        }
    }
}

