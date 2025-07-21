package com.example.uts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uts.AppDatabase;
import com.example.uts.MainActivity;
import com.example.uts.R;
import com.example.uts.Task;
import com.example.uts.User;

import java.util.List;

public class DashboardFragment extends Fragment {

    private TextView tvGreeting, progressText;
    private ProgressBar dailyProgress;
    private TextView workProgress, personalProgress, studyProgress;
    private TextView workTaskCount, personalTaskCount, studyTaskCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        tvGreeting = view.findViewById(R.id.tvGreeting);
        progressText = view.findViewById(R.id.progressText);
        dailyProgress = view.findViewById(R.id.dailyProgress);

        workProgress = view.findViewById(R.id.workProgress);
        personalProgress = view.findViewById(R.id.personalProgress);
        studyProgress = view.findViewById(R.id.studyProgress);

        workTaskCount = view.findViewById(R.id.workTaskCount);
        personalTaskCount = view.findViewById(R.id.personalTaskCount);
        studyTaskCount = view.findViewById(R.id.studyTaskCount);

        // Greeting
        User user = AppDatabase.getInstance(getContext()).userDao().getUserById(MainActivity.loggedInUserId);
        if (user != null) {
            tvGreeting.setText("Hello!\n" + user.username);
        }

        // View Task Button
        Button viewTasksButton = view.findViewById(R.id.viewTasksButton);
        viewTasksButton.setOnClickListener(v -> requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new TaskListFragment())
                .commit());

        calculateProgress();

        return view;
    }

    private void calculateProgress() {
        List<Task> tasks = AppDatabase.getInstance(getContext())
                .taskDao()
                .getTasksByUser(MainActivity.loggedInUserId);

        int total = tasks.size();
        int completed = 0;

        int workTotal = 0, workCompleted = 0;
        int personalTotal = 0, personalCompleted = 0;
        int studyTotal = 0, studyCompleted = 0;

        for (Task task : tasks) {
            if (task.isCompleted()) completed++;

            switch (task.getCategory().toLowerCase()) {
                case "work":
                    workTotal++;
                    if (task.isCompleted()) workCompleted++;
                    break;
                case "personal":
                    personalTotal++;
                    if (task.isCompleted()) personalCompleted++;
                    break;
                case "study":
                    studyTotal++;
                    if (task.isCompleted()) studyCompleted++;
                    break;
            }
        }

        // Update UI
        int percent = total > 0 ? (completed * 100 / total) : 0;
        dailyProgress.setProgress(percent);
        progressText.setText(percent + "%");

        workProgress.setText(workTotal > 0 ? (workCompleted * 100 / workTotal) + "%" : "0%");
        personalProgress.setText(personalTotal > 0 ? (personalCompleted * 100 / personalTotal) + "%" : "0%");
        studyProgress.setText(studyTotal > 0 ? (studyCompleted * 100 / studyTotal) + "%" : "0%");

        workTaskCount.setText(workTotal + " Tasks");
        personalTaskCount.setText(personalTotal + " Tasks");
        studyTaskCount.setText(studyTotal + " Tasks");
    }
}
