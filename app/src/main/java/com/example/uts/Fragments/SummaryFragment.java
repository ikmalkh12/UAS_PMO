package com.example.uts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.uts.Activity.MainActivity;
import com.example.uts.Database.AppDatabase;
import com.example.uts.views.CircleChartView;
import com.example.uts.R;
import com.example.uts.Database.Task;
import com.example.uts.Database.TaskDao;
import com.example.uts.Database.User;
import com.example.uts.Database.UserDao;

import java.util.List;

public class SummaryFragment extends Fragment {

    private TextView tvGreeting, taskRatio, progressText;
    private TextView workProgressText, personalProgressText, studyProgressText;

    private CircleChartView circleChart, workChart, personalChart, studyChart;;

    private TaskDao taskDao;
    private UserDao userDao;

    private int userId;

    public SummaryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        tvGreeting = view.findViewById(R.id.tvGreeting);
        progressText = view.findViewById(R.id.progressText);
        taskRatio = view.findViewById(R.id.taskRatio);
        circleChart = view.findViewById(R.id.circleChart);
        workProgressText = view.findViewById(R.id.workProgressText);
        personalProgressText = view.findViewById(R.id.personalProgressText);
        studyProgressText = view.findViewById(R.id.studyProgressText);
        workChart = view.findViewById(R.id.workChart);
        personalChart = view.findViewById(R.id.personalChart);
        studyChart = view.findViewById(R.id.studyChart);


        AppDatabase db = AppDatabase.getInstance(getContext());
        taskDao = db.taskDao();
        userDao = db.userDao();

        userId = MainActivity.loggedInUserId;

        loadDashboardData();

        return view;
    }

    private void loadDashboardData() {
        new Thread(() -> {
            List<Task> tasks = taskDao.getTasksByUser(userId);
            User user = userDao.getUserById(userId);

            int total = tasks.size();
            int done = 0;

            // Task groups
            int workTotal = 0, workDone = 0;
            int personalTotal = 0, personalDone = 0;
            int studyTotal = 0, studyDone = 0;

            for (Task t : tasks) {
                if (t.isCompleted()) done++;

                String cat = (t.getCategory() != null) ? t.getCategory().toLowerCase() : "";
                switch (cat) {
                    case "work":
                        workTotal++;
                        if (t.isCompleted()) workDone++;
                        break;
                    case "personal":
                        personalTotal++;
                        if (t.isCompleted()) personalDone++;
                        break;
                    case "study":
                        studyTotal++;
                        if (t.isCompleted()) studyDone++;
                        break;
                }
            }

            int percentage = total == 0 ? 0 : (int) ((done * 100f) / total);
            String ratio = done + "/" + total + " tasks done";

            int workPercent = workTotal == 0 ? 0 : (int) ((workDone * 100f) / workTotal);
            int personalPercent = personalTotal == 0 ? 0 : (int) ((personalDone * 100f) / personalTotal);
            int studyPercent = studyTotal == 0 ? 0 : (int) ((studyDone * 100f) / studyTotal);

            int finalWorkDone = workDone;
            int finalWorkTotal = workTotal;
            int finalPersonalDone = personalDone;
            int finalPersonalTotal = personalTotal;
            int finalStudyDone = studyDone;
            int finalStudyTotal = studyTotal;
            requireActivity().runOnUiThread(() -> {
                tvGreeting.setText("Hi, " + (user != null ? user.getUsername() : "Guest"));
                taskRatio.setText(ratio);
                progressText.setText(percentage + "%");
                circleChart.setProgress(percentage);

                workProgressText.setText(finalWorkDone + "/" + finalWorkTotal + " done");
                personalProgressText.setText(finalPersonalDone + "/" + finalPersonalTotal + " done");
                studyProgressText.setText(finalStudyDone + "/" + finalStudyTotal + " done");

                workChart.setProgress(workPercent);
                personalChart.setProgress(personalPercent);
                studyChart.setProgress(studyPercent);
            });
        }).start();
    }
}
