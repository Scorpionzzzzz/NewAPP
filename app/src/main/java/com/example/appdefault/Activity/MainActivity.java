package com.example.appdefault.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.appdefault.R;
import com.example.appdefault.fragments.CaNhanFragment;
import com.example.appdefault.fragments.HomeFragment;
import com.example.appdefault.fragments.ThucAnFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

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
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
