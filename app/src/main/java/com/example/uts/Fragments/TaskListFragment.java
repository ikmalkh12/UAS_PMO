package com.example.uts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts.Database.AppDatabase;
import com.example.uts.Activity.MainActivity;
import com.example.uts.R;
import com.example.uts.Database.Task;
import com.example.uts.Adapter.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment {

    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        recyclerView = view.findViewById(R.id.taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);

        loadTasks();

        return view;
    }

    private void loadTasks() {
        AppDatabase db = AppDatabase.getInstance(getContext());
        List<Task> userTasks = db.taskDao().getTasksByUser(MainActivity.loggedInUserId);

        taskList.clear();
        taskList.addAll(userTasks);
        taskAdapter.notifyDataSetChanged();
    }
}
