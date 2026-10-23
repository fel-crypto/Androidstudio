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

public class MainActivity extends AppCompatActivity {

    // IDs sugeridos: tilEmail, tilPassword, btnLogin, tvGoToRegister
    private TextInputLayout tilEmail, tilPassword;
    private Button btnLogin;
    private TextView tvGoToRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        initViews();

        // Lógica de Inicio de Sesión
        btnLogin.setOnClickListener(v -> {
            String email = Objects.requireNonNull(tilEmail.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(tilPassword.getEditText()).getText().toString().trim();

            if (validateFields(email, password)) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Navegación limpia al Feed (borra el historial de actividades)
                                Intent intent = new Intent(MainActivity.this, FeedActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        // Navegar a Registro
        tvGoToRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void initViews() {
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);
    }

    private boolean validateFields(String email, String password) {
        if (TextUtils.isEmpty(email)) { tilEmail.setError("Email requerido"); return false; }
        if (TextUtils.isEmpty(password)) { tilPassword.setError("Contraseña requerida"); return false; }
        tilEmail.setError(null); tilPassword.setError(null);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Persistencia de sesión: si ya está logueado, va directo al Feed
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, FeedActivity.class));
            finish();
        }
    }
}
