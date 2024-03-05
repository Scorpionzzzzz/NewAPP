package com.example.appdefault;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.appdefault.Database.DatabaseHelper;
import com.example.appdefault.fragments.RecipesFragment;
import com.example.appdefault.fragments.HealthFragment;
import com.example.appdefault.fragments.HomeFragment;
import com.example.appdefault.fragments.ReminderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the default fragment (HomeFragment)
        loadFragment(new HomeFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);

        // Set listener for Bottom Navigation View
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        loadFragment(new HomeFragment());
                        return true;
                    case R.id.menu_health:
                        loadFragment(new HealthFragment());
                        return true;
                    case R.id.menu_recipes:
                        loadFragment(new RecipesFragment());
                        return true;
                    case R.id.menu_reminder:
                        loadFragment(new ReminderFragment());
                        return true;
                    default:
                        return false;
                }
            }
        });
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        try {
            databaseHelper.createDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load fragment và thực hiện các thao tác khác
        loadFragment(new HomeFragment());

    }
    // Kiểm tra và sao chép cơ sở dữ liệu khi ứng dụng được khởi chạy lần đầu tiên



    // Method to load a fragment into the container
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

