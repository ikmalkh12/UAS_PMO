package com.example.uts.Fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts.AppDatabase;
import com.example.uts.Task;
import com.example.uts.R;
import com.example.uts.TaskAdapter;

import java.util.List;

public class TaskListFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private AppDatabase db;
    private List<Task> userTasks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        recyclerView = view.findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = AppDatabase.getInstance(getContext());
        int userId = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("user_id", -1);
        userTasks = db.taskDao().getTasksByUser(userId);

        adapter = new TaskAdapter(userTasks);
        recyclerView.setAdapter(adapter);

        return view;
    }
}


