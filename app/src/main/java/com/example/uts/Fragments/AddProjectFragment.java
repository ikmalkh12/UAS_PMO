package com.example.uts.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uts.Database.AppDatabase;
import com.example.uts.Activity.MainActivity;
import com.example.uts.R;
import com.example.uts.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AddProjectFragment extends Fragment {

    private TextInputEditText namaTask, deskTask;
    private EditText etTanggal, endTanggal;
    private Spinner spinner;
    private Button addButton;
    private ImageButton backButton, notifButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_project, container, false);

        // Inisialisasi komponen
        namaTask = view.findViewById(R.id.namaTask);
        deskTask = view.findViewById(R.id.deskTask);
        etTanggal = view.findViewById(R.id.etTanggal);
        endTanggal = view.findViewById(R.id.endTanggal);
        spinner = view.findViewById(R.id.spinner);
        addButton = view.findViewById(R.id.button3);
        backButton = view.findViewById(R.id.imageButton);
        notifButton = view.findViewById(R.id.imageButton2);

        // Isi spinner
        String[] kategori = {"Work", "Personal", "Study"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, kategori);
        spinner.setAdapter(adapter);

        // DatePicker
        etTanggal.setOnClickListener(v -> showDatePicker(etTanggal));
        endTanggal.setOnClickListener(v -> showDatePicker(endTanggal));

        addButton.setOnClickListener(v -> {
            String name = namaTask.getText().toString().trim();
            String desc = deskTask.getText().toString().trim();
            String start = etTanggal.getText().toString().trim();
            String end = endTanggal.getText().toString().trim();
            String category = spinner.getSelectedItem().toString();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
                Toast.makeText(getContext(), "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            Task task = new Task(
                    MainActivity.loggedInUserId,
                    name, desc, start, end, category, false
            );
            AppDatabase.getInstance(getContext()).taskDao().insert(task);

            Toast.makeText(getContext(), "Project berhasil ditambahkan", Toast.LENGTH_SHORT).show();

            // Kosongkan
            namaTask.setText("");
            deskTask.setText("");
            etTanggal.setText("");
            endTanggal.setText("");
        });

        backButton.setOnClickListener(v -> requireActivity().onBackPressed());
        notifButton.setOnClickListener(v -> Toast.makeText(getContext(), "Notifikasi belum tersedia", Toast.LENGTH_SHORT).show());

        return view;
    }

    private void showDatePicker(EditText field) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            String formatted = dayOfMonth + "/" + (month + 1) + "/" + year;
            field.setText(formatted);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
