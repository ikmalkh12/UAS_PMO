package com.example.uts;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AddProject extends AppCompatActivity {

    Spinner spinner;
    TextInputEditText namaTask, deskTask;
    EditText etTanggal, endTanggal;
    Button buttonAddProject;
    ImageButton backButton, notifButton;
    TextView headerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_project); // pastikan nama file XML sesuai

        // Inisialisasi komponen UI
        spinner = findViewById(R.id.spinner);
        namaTask = findViewById(R.id.namaTask);
        deskTask = findViewById(R.id.deskTask);
        etTanggal = findViewById(R.id.etTanggal);
        endTanggal = findViewById(R.id.endTanggal);
        buttonAddProject = findViewById(R.id.button3);
        backButton = findViewById(R.id.imageButton);
        notifButton = findViewById(R.id.imageButton2);
        headerTitle = findViewById(R.id.textView3);

        // Spinner setup
        String[] taskGroups = {"Work", "Personal", "Study"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, taskGroups);
        spinner.setAdapter(adapter);

        // Tanggal picker awal
        etTanggal.setOnClickListener(v -> showDatePickerDialog(etTanggal));

        // Tanggal picker akhir
        endTanggal.setOnClickListener(v -> showDatePickerDialog(endTanggal));

        // Tombol tambah project
        buttonAddProject.setOnClickListener(v -> {
            String projectName = namaTask.getText().toString();
            String description = deskTask.getText().toString();
            String startDate = etTanggal.getText().toString();
            String endDate = endTanggal.getText().toString();

            if (projectName.isEmpty() || description.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi semua data!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Project berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                // Tambahkan logika penyimpanan project di sini
            }
        });

        // Tombol kembali
        backButton.setOnClickListener(v -> onBackPressed());

        // Tombol notifikasi (dummy)
        notifButton.setOnClickListener(v -> Toast.makeText(this, "Notifikasi belum diimplementasi", Toast.LENGTH_SHORT).show());
    }

    private void showDatePickerDialog(EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = String.format("%02d %s, %d", dayOfMonth, getMonthName(month1), year1);
                    editText.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private String getMonthName(int month) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return months[month];
    }
}
