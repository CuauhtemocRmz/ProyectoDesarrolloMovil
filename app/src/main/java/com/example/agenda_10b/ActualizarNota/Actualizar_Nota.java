package com.example.agenda_10b.ActualizarNota;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.agenda_10b.R;

public class Actualizar_Nota extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView Id_nota_A, Uid_Usuario_A,Correo_Usuario_A, Fecha_registro_A, Fecha_A, Estado_A, Estado_nuevo;
    EditText Titulo_A, Descripcion_A;
    Button Btn_Calendario_A;

    String id_nota_R, Uid_Usuario_R, Correo_Usuario_R, Fecha_registro_R, Titulo_R, Descripcion_R, Fecha_R, Estado_R;

    ImageView Tarea_Finalizada, Tarea_No_Finalizada;

    Spinner Spinner_estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_nota);
        Inicializarvistas();
        RecuperarDatos();
        SetearDatos();
        ComprobarEstadoNota();
        Spinner_estado();
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

    private void Spinner_estado(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Estados_nota, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_estado.setAdapter(adapter);
        Spinner_estado.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String estado_seleccionado = adapterView.getItemAtPosition(i).toString();
        Estado_nuevo.setText(estado_seleccionado);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}