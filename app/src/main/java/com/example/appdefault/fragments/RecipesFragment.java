package com.example.appdefault.fragments;

import static com.example.appdefault.Database.FoodDBHelper.COLUMN_FOOD_NAME;
import static com.example.appdefault.Database.FoodDBHelper.TABLE_FOOD;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdefault.FoodAdapter.FoodAdapter;
import com.example.appdefault.FoodAdapter.FoodItem;
import com.example.appdefault.R;
import com.example.appdefault.Database.FoodDBHelper;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipesFragment extends Fragment {

    private EditText editTextSearch;
    private ImageButton buttonSearch;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private FoodDBHelper foodDBHelper;

    public RecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);

        editTextSearch = view.findViewById(R.id.editTextSearch);
        buttonSearch = view.findViewById(R.id.buttonSearchRecipes);
        recyclerView = view.findViewById(R.id.recyclerViewRecipes);

        foodDBHelper = new FoodDBHelper(requireContext());

        foodAdapter = new FoodAdapter(new ArrayList<>()); // Tạo Adapter của bạn

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(foodAdapter);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        return view;
    }

    private void performSearch() {
        String searchTerm = editTextSearch.getText().toString().trim();

        // Chuyển đổi searchTerm và tên thực phẩm về dạng chữ thường hoặc chữ hoa
        String lowercaseSearchTerm = searchTerm.toLowerCase(Locale.getDefault());

        // Sử dụng hàm LOWER để chuyển đổi tên thực phẩm về dạng chữ thường
        String query = "SELECT * FROM " + FoodDBHelper.TABLE_FOOD + " WHERE LOWER(" + FoodDBHelper.COLUMN_FOOD_NAME + ") LIKE ?";

        // Thực hiện truy vấn SQL SELECT với điều kiện là tên thực phẩm
        List<FoodItem> searchResults = foodDBHelper.searchFoodByName(query, new String[]{"%" + lowercaseSearchTerm + "%"});

        // Cập nhật dữ liệu cho Adapter
        foodAdapter.setFoodItems(searchResults);
    }


}


