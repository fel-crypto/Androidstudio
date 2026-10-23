package com.beta.morefilms;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    // IDs sugeridos: tilEmailRegister, tilPasswordRegister, tilConfirmPassword, btnRegister, tvBackToLogin
    private TextInputLayout tilEmail, tilPassword, tilConfirm;
    private Button btnRegister;
    private TextView tvBackToLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        initViews();

        btnRegister.setOnClickListener(v -> {
            String email = Objects.requireNonNull(tilEmail.getEditText()).getText().toString().trim();
            String pass = Objects.requireNonNull(tilPassword.getEditText()).getText().toString().trim();
            String confirm = Objects.requireNonNull(tilConfirm.getEditText()).getText().toString().trim();

            if (pass.equals(confirm) && !TextUtils.isEmpty(email) && pass.length() >= 6) {
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "¡Bienvenido a MoreFilms!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(this, FeedActivity.class));
                                finishAffinity(); // Cierra todas las actividades previas
                            } else {
                                Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
                Toast.makeText(this, "Revisa los campos (Min. 6 caracteres)", Toast.LENGTH_SHORT).show();
            }
        });

        // Volver al Login limpiamente
        tvBackToLogin.setOnClickListener(v -> finish());
    }

    private void initViews() {
        tilEmail = findViewById(R.id.tilEmailRegister);
        tilPassword = findViewById(R.id.tilPasswordRegister);
        tilConfirm = findViewById(R.id.tilConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);
    }
}