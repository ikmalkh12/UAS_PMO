package com.example.uts.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts.Activity.TaskDetailActivity;
import com.example.uts.R;
import com.example.uts.Database.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> taskList;
    private OnTaskDeleteListener deleteListener;

    // Interface untuk callback hapus
    public interface OnTaskDeleteListener {
        void onDelete(Task task);
    }

    // Setter listener
    public void setOnTaskDeleteListener(OnTaskDeleteListener listener) {
        this.deleteListener = listener;
    }

    // Konstruktor
    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    // ViewHolder
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, taskDesc, taskTime;
        Button btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskDesc = itemView.findViewById(R.id.taskSubtitle);
            taskTime = itemView.findViewById(R.id.taskTime);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.taskTitle.setText(task.getName());
        holder.taskDesc.setText(task.getCategory());
        holder.taskTime.setText(task.getStartDate());

        // Buka detail saat item diklik
        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, TaskDetailActivity.class);
            intent.putExtra("task_id", task.getId()); // Pastikan key-nya sesuai dengan TaskDetailActivity
            context.startActivity(intent);
        });

        // Hapus data saat tombol delete ditekan
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // Method bantu untuk hapus task dari adapter
    public void removeTask(Task task) {
        int pos = taskList.indexOf(task);
        if (pos != -1) {
            taskList.remove(pos);
            notifyItemRemoved(pos);
        }
    }
}
