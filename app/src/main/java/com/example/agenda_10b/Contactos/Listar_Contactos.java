package com.example.agenda_10b.Contactos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.agenda_10b.R;

public class Listar_Contactos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_contactos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_agregar_contacto, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Agregar_Contacto){
            String Uid_Recuperado = getIntent().getStringExtra("Uid");
            Intent intent = new Intent(Listar_Contactos.this, Agregar_Contacto.class);
            intent.putExtra("Uid", Uid_Recuperado);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}