package com.example.agenda_10b;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Pantalla_De_Carga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_carga);

        int tiempo = 3000;
        new Handler().postDelayed(() -> {
            startActivity(new Intent(Pantalla_De_Carga.this,MainActivity.class));
            finish();
        },tiempo);
    }
}
