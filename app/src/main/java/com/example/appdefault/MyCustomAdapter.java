package com.example.appdefault;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.appdefault.FoodAdapter.FoodItem;

import java.util.List;

public class MyCustomAdapter extends BaseAdapter {

    private List<FoodItem> foodItems;
    private Context context;

    public MyCustomAdapter(Context context, List<FoodItem> foodItems) {
        this.context = context;
        this.foodItems = foodItems;
    }

    @Override
    public int getCount() {
        return foodItems.size();
    }

    @Override
    public Object getItem(int position) {
        return foodItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Trả về ID của mục tại vị trí position trong danh sách
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Thực hiện việc gán dữ liệu và tạo giao diện cho mỗi mục trong danh sách
        convertView.setBackgroundResource(R.drawable.textview_bg);
        // Trả về View của mục tại vị trí position
        return convertView;
    }
}

