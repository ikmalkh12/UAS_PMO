package com.example.uts.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.uts.R;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uts.Database.AppDatabase;
import com.example.uts.Database.Task;

public class TaskDetailActivity extends AppCompatActivity {

    private EditText editTitle, editDescription;
    private TextView textCategory, textDate, textEndDate, textStatus;
    private CheckBox checkDone;
    private Button btnSave;
    private ImageButton btnBack;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail); // pastikan nama file XML sesuai

        // Inisialisasi komponen dari layout
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        textCategory = findViewById(R.id.textCategory);
        textDate = findViewById(R.id.textDate);
        textEndDate = findViewById(R.id.textEndDate);
        textStatus = findViewById(R.id.textStatus);
        checkDone = findViewById(R.id.checkDone);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        // Ambil ID dari intent
        int taskId = getIntent().getIntExtra("task_id", -1);
        if (taskId != -1) {
            task = AppDatabase.getInstance(this).taskDao().getTaskById(taskId);
            if (task != null) {
                editTitle.setText(task.getName());
                editDescription.setText(task.getDescription());
                textCategory.setText("Kategori: " + task.getCategory());
                textDate.setText("Tanggal Mulai: " + task.getStartDate());
                textEndDate.setText("Tanggal Selesai: " + task.getEndDate());
                textStatus.setText(task.isCompleted() ? "Status: Selesai" : "Status: Belum Selesai");
                checkDone.setChecked(task.isCompleted());
            }
        }

        btnSave.setOnClickListener(v -> updateTask());

        btnBack.setOnClickListener(v -> finish());
    }

    private void updateTask() {
        if (task == null) {
            Toast.makeText(this, "Tugas tidak ditemukan", Toast.LENGTH_SHORT).show();
            return;
        }

        String updatedTitle = editTitle.getText().toString().trim();
        String updatedDescription = editDescription.getText().toString().trim();
        boolean isCompleted = checkDone.isChecked();

        if (updatedTitle.isEmpty()) {
            Toast.makeText(this, "Judul tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        task.setName(updatedTitle);
        task.setDescription(updatedDescription);
        task.setCompleted(isCompleted);

        AppDatabase.getInstance(this).taskDao().update(task);
        Toast.makeText(this, "Tugas berhasil diperbarui", Toast.LENGTH_SHORT).show();
        finish();
    }
}
