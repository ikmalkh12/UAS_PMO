package com.example.uts.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uts.Database.AppDatabase;
import com.example.uts.R;
import com.example.uts.Database.Task;

public class TaskDetailActivity extends AppCompatActivity {

    private EditText editTitle, editDescription;
    private TextView textCategory, textDate, textEndDate;
    private CheckBox checkDone;
    private Button btnSave;
    private AppDatabase db;
    private Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Edit Tugas");
        }


        // Inisialisasi view
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        checkDone = findViewById(R.id.checkDone);
        btnSave = findViewById(R.id.btnSave);
        textCategory = findViewById(R.id.textCategory);
        textDate = findViewById(R.id.textDate);
        textEndDate = findViewById(R.id.textEndDate);

        db = AppDatabase.getInstance(this);

        int taskId = getIntent().getIntExtra("TASK_ID", -1);
        if (taskId != -1) {
            currentTask = db.taskDao().getTaskById(taskId);
            if (currentTask != null) {
                editTitle.setText(currentTask.getName());
                editDescription.setText(currentTask.getDescription());
                checkDone.setChecked(currentTask.isCompleted());
                textCategory.setText("Kategori: " + currentTask.getCategory());
                textDate.setText("Mulai: " + currentTask.getStartDate());
                textEndDate.setText("Selesai: " + currentTask.getEndDate());
            }
        }

        btnSave.setOnClickListener(v -> {
            if (currentTask != null) {
                currentTask.setName(editTitle.getText().toString());
                currentTask.setDescription(editDescription.getText().toString());
                currentTask.setCompleted(checkDone.isChecked());
                db.taskDao().update(currentTask);
                Toast.makeText(this, "Tugas berhasil diperbarui", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
