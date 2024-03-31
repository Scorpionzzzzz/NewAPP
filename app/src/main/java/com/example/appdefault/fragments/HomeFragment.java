package com.example.appdefault.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.appdefault.FoodAdapter.Meal;
import com.example.appdefault.R;
import com.example.appdefault.FoodAdapter.MealAdapter;
import com.example.appdefault.Database.MealDBHelper;

import java.util.List;

public class HomeFragment extends Fragment {

    private ListView mBreakfastListView;
    private ListView mLunchListView;
    private ListView mDinnerListView;
    private MealAdapter mBreakfastAdapter;
    private MealAdapter mLunchAdapter;
    private MealAdapter mDinnerAdapter;
    private MealDBHelper mMealDBHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mBreakfastListView = view.findViewById(R.id.listviewBreakfast);

        mLunchListView = view.findViewById(R.id.listviewLunch);
        mDinnerListView = view.findViewById(R.id.listViewDinner);
        mMealDBHelper = new MealDBHelper(getActivity());

        // Load meals for breakfast
        List<Meal> breakfastList = mMealDBHelper.getMealsByType("Bữa sáng");
        mBreakfastAdapter = new MealAdapter(getActivity(), breakfastList);

        mBreakfastListView.setAdapter(mBreakfastAdapter);

        // Load meals for lunch
        List<Meal> lunchList = mMealDBHelper.getMealsByType("Bữa trưa");
        mLunchAdapter = new MealAdapter(getActivity(), lunchList);
        mLunchListView.setAdapter(mLunchAdapter);

        // Load meals for dinner
        List<Meal> dinnerList = mMealDBHelper.getMealsByType("Bữa tối");
        mDinnerAdapter = new MealAdapter(getActivity(), dinnerList);
        mDinnerListView.setAdapter(mDinnerAdapter);

        return view;
    }
}
