package com.example.appdefault.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.appdefault.R;
import com.example.appdefault.fragments.CaNhanFragment;
import com.example.appdefault.fragments.HomeFragment;
import com.example.appdefault.fragments.ThucAnFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mealButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the default fragment (HomeFragment)
        loadFragment(new HomeFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);

        // Set listener for Bottom Navigation View
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        loadFragment(new HomeFragment());
                        return true;
                    case R.id.menu_thuc_an:
                        loadFragment(new ThucAnFragment());
                        return true;
                    case R.id.menu_ca_nhan:
                        loadFragment(new CaNhanFragment());
                        return true;
                    default:
                        return false;
                }
            }
        });





        // Floating Action Button
        FloatingActionButton mealButton = findViewById(R.id.mealButton);
        mealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.meal_options_menu, popupMenu.getMenu());

                // Đặt sự kiện lắng nghe khi một mục menu được chọn
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_bua_sang:
                                // Xử lý khi chọn bữa sáng
                                return true;
                            case R.id.menu_bua_trua:
                                // Xử lý khi chọn bữa trưa
                                return true;
                            case R.id.menu_bua_toi:
                                // Xử lý khi chọn bữa tối
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                // Hiển thị PopupMenu
                popupMenu.show();
            }
        });

    }
    private void showMealOptions(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.meal_options_menu, popupMenu.getMenu());

        // Set item click listener
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_bua_sang:
                        // Handle "Bữa Sáng" selection
                        return true;
                    case R.id.menu_bua_trua:
                        // Handle "Bữa Trưa" selection
                        return true;
                    case R.id.menu_bua_toi:
                        // Handle "Bữa Tối" selection
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
