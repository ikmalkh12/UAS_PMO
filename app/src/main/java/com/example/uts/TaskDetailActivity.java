package com.example.uts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView textTitle, textCategory, textDate, textEndDate, textDescription, textStatus;
    private Button btnComplete;
    private AppDatabase db;
    private Task currentTask;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Kembali ke sebelumnya
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Task Detail");

        setContentView(R.layout.activity_task_detail);

        textTitle = findViewById(R.id.textTitle);
        textCategory = findViewById(R.id.textCategory);
        textDate = findViewById(R.id.textDate);
        textEndDate = findViewById(R.id.textEndDate);
        textDescription = findViewById(R.id.textDescription);
        textStatus = findViewById(R.id.textStatus);
        btnComplete = findViewById(R.id.btnComplete);

        db = AppDatabase.getInstance(this);

        int taskId = getIntent().getIntExtra("taskId", -1);
        if (taskId != -1) {
            currentTask = db.taskDao().getTaskById(taskId);
            if (currentTask != null) {
                textTitle.setText(currentTask.getName());
                textCategory.setText("Kategori: " + currentTask.getCategory());
                textDate.setText("Mulai: " + currentTask.getStartDate());
                textEndDate.setText("Selesai: " + currentTask.getEndDate());
                textDescription.setText(currentTask.getDescription());
                textStatus.setText(currentTask.isCompleted() ? "Status: Selesai" : "Status: Belum Selesai");

                if (currentTask.isCompleted()) {
                    btnComplete.setEnabled(false);
                    btnComplete.setText("Sudah Selesai");
                }
            }
        }

        btnComplete.setOnClickListener(v -> {
            if (currentTask != null && !currentTask.isCompleted()) {
                currentTask.setCompleted(true);
                db.taskDao().update(currentTask);
                textStatus.setText("Status: Selesai");
                btnComplete.setEnabled(false);
                btnComplete.setText("Sudah Selesai");
                Toast.makeText(this, "Tugas ditandai selesai", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
