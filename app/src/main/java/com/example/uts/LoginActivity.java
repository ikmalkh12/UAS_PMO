package com.example.uts;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnToRegister = findViewById(R.id.btnToRegister);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Isi username dan password", Toast.LENGTH_SHORT).show();
                return;
            }

            AppDatabase db = AppDatabase.getInstance(this);
            User user = db.userDao().getUserByUsername(username);

            if (user == null || !user.password.equals(password)) {
                Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show();
            } else {
                MainActivity.loggedInUserId = user.id;
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });

        btnToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

    }
}
