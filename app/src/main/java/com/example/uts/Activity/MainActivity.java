package com.example.uts.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.uts.Fragments.AddProjectFragment;
import com.example.uts.Fragments.DashboardFragment;
import com.example.uts.Fragments.TaskListFragment;
import com.example.uts.R;
import com.example.uts.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ambil userId dari Intent
        if (getIntent() != null && getIntent().hasExtra("userId")) {
            loggedInUserId = getIntent().getIntExtra("userId", -1);
        }

        if (loggedInUserId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment fragment;

            if (itemId == R.id.nav_tasks) {
                fragment = new TaskListFragment();
            } else if (itemId == R.id.nav_add) {
                fragment = new AddProjectFragment();
            } else if (itemId == R.id.nav_dashboard) {
                fragment = new DashboardFragment();
            } else {
                fragment = null;
            }

            if (fragment != null) {
                loadFragment(fragment);
                return true;
            }
            return false;
        });

        loadFragment(new DashboardFragment());
    }




    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
