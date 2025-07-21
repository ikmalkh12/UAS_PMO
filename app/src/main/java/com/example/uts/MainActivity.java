package com.example.uts;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.uts.Fragments.AddProjectFragment;
import com.example.uts.Fragments.DashboardFragment;
import com.example.uts.Fragments.TaskListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_tasks:
                    fragment = new TaskListFragment();
                    break;
                case R.id.nav_add:
                    fragment = new AddProjectFragment();
                    break;
                case R.id.nav_dashboard:
                    fragment = new DashboardFragment();
                    break;
            }
            if (fragment != null) loadFragment(fragment);
            return true;
        });

        if (loggedInUserId == -1) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        } else {
            loadFragment(new DashboardFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
