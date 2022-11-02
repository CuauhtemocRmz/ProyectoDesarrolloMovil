package com.example.agenda_10b.ActualizarNota;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agenda_10b.AgregarNota.Agregar_Nota;
import com.example.agenda_10b.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Actualizar_Nota extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView Id_nota_A, Uid_Usuario_A,Correo_Usuario_A, Fecha_registro_A, Fecha_A, Estado_A, Estado_nuevo;
    EditText Titulo_A, Descripcion_A;
    Button Btn_Calendario_A;

    String id_nota_R, Uid_Usuario_R, Correo_Usuario_R, Fecha_registro_R, Titulo_R, Descripcion_R, Fecha_R, Estado_R;

    ImageView Tarea_Finalizada, Tarea_No_Finalizada;

    Spinner Spinner_estado;
    int dia,mes,anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_nota);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Actualizar nota");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Inicializarvistas();
        RecuperarDatos();
        SetearDatos();
        ComprobarEstadoNota();
        Spinner_estado();

        Btn_Calendario_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeleccionarFecha();
            }
        });
    }

    private void Inicializarvistas(){

        Id_nota_A = findViewById(R.id.Id_nota_A);
        Uid_Usuario_A = findViewById(R.id.Uid_Usuario_A);
        Correo_Usuario_A = findViewById(R.id.Correo_Usuario_A);
        Fecha_registro_A = findViewById(R.id.Fecha_registro_A);
        Fecha_A = findViewById(R.id.Fecha_A);
        Estado_A = findViewById(R.id.Estado_A);
        Titulo_A = findViewById(R.id.Titulo_A);
        Descripcion_A = findViewById(R.id.Descripcion_A);
        Btn_Calendario_A = findViewById(R.id.Btn_Calendario_A);
        Tarea_Finalizada = findViewById(R.id.Tarea_Finalizada);
        Tarea_No_Finalizada = findViewById(R.id.Tarea_No_Finalizada);

        Spinner_estado = findViewById(R.id.Spiner_estado);
        Estado_nuevo = findViewById(R.id.Estado_nuevo);
    }

    private void RecuperarDatos(){

        Bundle intent = getIntent().getExtras();

        id_nota_R = intent.getString("id_nota");
        Uid_Usuario_R = intent.getString("uid_usuario");
        Correo_Usuario_R = intent.getString("correo_usuario");
        Fecha_registro_R = intent.getString("fecha_registro");
        Titulo_R = intent.getString("titulo");
        Descripcion_R = intent.getString("descripcion");
        Fecha_R = intent.getString("fecha_nota");
        Estado_R = intent.getString("estado");


    }

    private void SetearDatos(){
        Id_nota_A.setText(id_nota_R);
        Uid_Usuario_A.setText(Uid_Usuario_R);
        Correo_Usuario_A.setText(Correo_Usuario_R);
        Fecha_registro_A.setText(Fecha_registro_R);
        Fecha_A.setText(Titulo_R);
        Estado_A.setText(Descripcion_R);
        Titulo_A.setText(Fecha_R);
        Estado_A.setText(Estado_R);
    }

    private void ComprobarEstadoNota(){
        String estado_nota = Estado_A.getText().toString();

        if(estado_nota.equals("No finalizado")){
            Tarea_No_Finalizada.setVisibility(View.VISIBLE);
        }
        if(estado_nota.equals("Finalizado")){
            Tarea_Finalizada.setVisibility(View.VISIBLE);
        }

    }

    private void SeleccionarFecha(){
        final Calendar calendario = Calendar.getInstance();

        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        anio = calendario.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Actualizar_Nota.this, (datePicker, AnioSeleccionado, MesSeleccionado, DiaSeleccionado) -> {

            String diaFormateado, mesFormateado;

            //OBTENER DIA
            if (DiaSeleccionado < 10){
                diaFormateado = "0"+String.valueOf(DiaSeleccionado);
                // Antes: 9/11/2022 -  Ahora 09/11/2022
            }else {
                diaFormateado = String.valueOf(DiaSeleccionado);
                //Ejemplo 13/08/2022
            }

            //OBTENER EL MES
            int Mes = MesSeleccionado + 1;

            if (Mes < 10){
                mesFormateado = "0"+String.valueOf(Mes);
                // Antes: 09/8/2022 -  Ahora 09/08/2022
            }else {
                mesFormateado = String.valueOf(Mes);
                //Ejemplo 13/10/2022 - 13/11/2022 - 13/12/2022

            }

            //Setear fecha en TextView
            Fecha_A.setText(diaFormateado + "/" + mesFormateado + "/"+ AnioSeleccionado);

        }
                ,anio,mes,dia);
        datePickerDialog.show();
    }


    private void Spinner_estado(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Estados_nota, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_estado.setAdapter(adapter);
        Spinner_estado.setOnItemSelectedListener(this);
    }

    private void Actualizar_NotaBD(){
        String tituloActualizar = Titulo_A.getText().toString();
        String descripcionActualizar = Descripcion_A.getText().toString();
        String fechaActualziar = Fecha_A.getText().toString();
        String estadoActualizar = Estado_nuevo.getText().toString();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Notas_Publicadas");

        //Consulta
        Query query = databaseReference.orderByChild("id_nota").equalTo(id_nota_R);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    ds.getRef().child("titulo").setValue(tituloActualizar);
                    ds.getRef().child("descripcion").setValue(descripcionActualizar);
                    ds.getRef().child("fecha_nota").setValue(fechaActualziar);
                    ds.getRef().child("estado").setValue(estadoActualizar);
                }
                Toast.makeText(Actualizar_Nota.this, "Nota actualizada con exito", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String ESTADO_ACTUAL = Estado_A.getText().toString();
        String Posicion_1 = adapterView.getItemAtPosition(1).toString();

        String estado_seleccionado = adapterView.getItemAtPosition(i).toString();
        Estado_nuevo.setText(estado_seleccionado);

        if (ESTADO_ACTUAL.equals("Finalizado")){
            Estado_nuevo.setText(Posicion_1);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_actualizar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Actualizar_Nota_BD:
                Actualizar_NotaBD();
                //Toast.makeText(this, "Nota Actualizada", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}