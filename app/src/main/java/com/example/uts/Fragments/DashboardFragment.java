package com.example.uts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uts.AppDatabase;
import com.example.uts.MainActivity;
import com.example.uts.R;
import com.example.uts.Task;

import java.util.List;

public class DashboardFragment extends Fragment {
    private ProgressBar dailyProgress;
    private TextView progressText, workProgress, personalProgress, studyProgress;
    private LinearLayout inProgressContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        dailyProgress = view.findViewById(R.id.dailyProgress);
        progressText = view.findViewById(R.id.progressText);
        workProgress = view.findViewById(R.id.workProgress);
        personalProgress = view.findViewById(R.id.personalProgress);
        studyProgress = view.findViewById(R.id.studyProgress);
        inProgressContainer = view.findViewById(R.id.inProgressContainer);

        Button viewTasksButton = view.findViewById(R.id.viewTasksButton);
        viewTasksButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new TaskListFragment()).commit());

        loadDashboard();
        return view;
    }

    private void loadDashboard() {
        int userId = MainActivity.loggedInUserId;
        List<Task> taskList = AppDatabase.getInstance(requireContext()).taskDao().getTasksByUser(userId);

        int total = taskList.size(), completed = 0, work = 0, workC = 0, personal = 0, personalC = 0, study = 0, studyC = 0;
        for (Task task : taskList) {
            if (task.isCompleted()) completed++;
            switch (task.getCategory().toLowerCase()) {
                case "work": work++; if (task.isCompleted()) workC++; break;
                case "personal": personal++; if (task.isCompleted()) personalC++; break;
                case "study": study++; if (task.isCompleted()) studyC++; break;
            }
        }

        dailyProgress.setProgress(total > 0 ? (completed * 100 / total) : 0);
        progressText.setText((total > 0 ? (completed * 100 / total) : 0) + "%");
        workProgress.setText(work > 0 ? (workC * 100 / work) + "%" : "0%");
        personalProgress.setText(personal > 0 ? (personalC * 100 / personal) + "%" : "0%");
        studyProgress.setText(study > 0 ? (studyC * 100 / study) + "%" : "0%");
    }
}