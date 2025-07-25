package com.example.uts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.uts.Database.AppDatabase;
import com.example.uts.views.CircleChartView;
import com.example.uts.R;
import com.example.uts.SessionManager;
import com.example.uts.Task;
import com.example.uts.TaskDao;
import com.example.uts.User;
import com.example.uts.UserDao;

import java.util.List;

public class DashboardFragment extends Fragment {

    private TextView tvGreeting, taskRatio, progressText;
    private CircleChartView circleChart;

    private AppDatabase db;
    private TaskDao taskDao;
    private UserDao userDao;

    private int userId;

    public DashboardFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        tvGreeting = view.findViewById(R.id.tvGreeting);
        progressText = view.findViewById(R.id.progressText);
        taskRatio = view.findViewById(R.id.taskRatio);
        circleChart = view.findViewById(R.id.circleChart);

        db = AppDatabase.getInstance(getContext());
        taskDao = db.taskDao();
        userDao = db.userDao();

        SessionManager sessionManager = new SessionManager(requireContext());
        userId = sessionManager.getLoggedInUserId();

        loadDashboardData();

        return view;
    }

    private void loadDashboardData() {
        new Thread(() -> {
            List<Task> tasks = taskDao.getTasksByUser(userId);
            User user = userDao.getUserById(userId);

            int total = tasks.size();
            int done = 0;
            for (Task t : tasks) {
                if (t.isCompleted()) done++;
            }

            int percentage = total == 0 ? 0 : (int) ((done * 100f) / total);
            String ratio = done + "/" + total + " tasks done";

            requireActivity().runOnUiThread(() -> {
                tvGreeting.setText("Hi, " + user.getUsername());
                taskRatio.setText(ratio);
                progressText.setText(percentage + "%");
                circleChart.setProgress(percentage);
            });
        }).start();
    }
}
