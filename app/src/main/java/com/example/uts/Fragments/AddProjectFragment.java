package com.example.uts.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uts.AppDatabase;
import com.example.uts.MainActivity;
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

        // Inisialisasi komponen sesuai dengan ID XML
        namaTask = view.findViewById(R.id.namaTask);
        deskTask = view.findViewById(R.id.deskTask);
        etTanggal = view.findViewById(R.id.etTanggal);
        endTanggal = view.findViewById(R.id.endTanggal);
        spinner = view.findViewById(R.id.spinner);
        addButton = view.findViewById(R.id.button3);
        backButton = view.findViewById(R.id.imageButton);
        notifButton = view.findViewById(R.id.imageButton2);

        // Listener untuk memilih tanggal
        etTanggal.setOnClickListener(v -> showDatePicker(etTanggal));
        endTanggal.setOnClickListener(v -> showDatePicker(endTanggal));

        // Tombol Add Project
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

            Task task = new Task(name, desc, start, end, category, false, MainActivity.loggedInUserId);
            AppDatabase.getInstance(getContext()).taskDao().insert(task);
            Toast.makeText(getContext(), "Project berhasil ditambahkan", Toast.LENGTH_SHORT).show();

            // Kosongkan input setelah ditambahkan
            namaTask.setText("");
            deskTask.setText("");
            etTanggal.setText("");
            endTanggal.setText("");
        });

        // Tombol kembali (dummy)
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        // Tombol notifikasi (dummy)
        notifButton.setOnClickListener(v -> Toast.makeText(getContext(), "Notifikasi belum tersedia", Toast.LENGTH_SHORT).show());

        return view;
    }

    private void showDatePicker(EditText field) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    String formattedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    field.setText(formattedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}
