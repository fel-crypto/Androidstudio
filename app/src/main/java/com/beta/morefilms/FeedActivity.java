package com.beta.morefilms;import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // ID sugerido: bottomNavigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        // Lógica de navegación del menú inferior
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            // Implementación del botón 'Cerrar Sesión' dentro de la opción Perfil
            if (id == R.id.nav_profile) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(FeedActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            }
            return true;
        });
    }
}