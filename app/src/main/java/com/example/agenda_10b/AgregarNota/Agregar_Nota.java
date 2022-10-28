package com.example.agenda_10b.AgregarNota;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agenda_10b.Objetos.Nota;
import com.example.agenda_10b.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Agregar_Nota extends AppCompatActivity {

    TextView Uid_Usuario, Correo_Usuario, Fecha_hora_actual, Fecha, Estado;
    EditText Titulo, Descripcion;
    Button Btn_Calendario;

    int dia, mes, anio;

    DatabaseReference BD_Firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);

        ActionBar actionBar = getSupportActionBar();
        ActionBar.setTitle("");
        ActionBar.setDisplayShowHomeEnabled(true);
        ActionBar.setDisplayHomeAsUpEnabled(true);

        InicializarVariables();
        ObtenerDatos();
        Obtener_Fecha_Hora_Actual();

        Btn_Calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();

                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio = calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Agregar_Nota.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anioSeleccionado, int MesSeleccionado, int DiaSeleccionado) {

                        String diaFormateado, mesFormateado;

                        //OBTENER DIA
                        if (DiaSeleccionado < 10) {
                            diaFormateado = "0" + String.valueOf(DiaSeleccionado);
                            //Antes: 9/11/2022 - Ahora 09/11/2022
                        } else {
                            diaFormateado = String.valueOf(DiaSeleccionado);
                            //Ejemplo 13/08/2022
                        }

                        //OBTENER EL MES
                        int Mes = MesSeleccionado + 1;

                        if (Mes < 10) {
                            mesFormateado = "0" + String.valueOf(Mes);
                            //Antes: 9/08/2022 - Ahora 09/08/2022
                        } else {
                            mesFormateado = String.valueOf(Mes);
                            //Ejemplo 13/10/2022 - 13/11/2022 - 13/12/2022
                        }

                        //Setear fecha en TextView
                        Fecha.setText(diaFormateado + "/" + mesFormateado + "/" + anioSeleccionado);

                    }
                }
                ,anio,mes,dia);
                datePickerDialog.show();
            }
        });
    }

    private void InicializarVariables(){
        Uid_Usuario = findViewById(R.id.Uid_Usuario);
        Correo_Usuario = findViewById(R.id.Correo_Usuario);
        Fecha_hora_actual = findViewById(R.id.Fecha_hora_actual);
        Fecha = findViewById(R.id.Fecha);
        Estado = findViewById(R.id.Estado);

        Titulo = findViewById(R.id.Titulo);
        Descripcion = findViewById(R.id.Descripcion);

        Btn_Calendario = findViewById(R.id.Btn_Calendario);

        BD_Firebase = FirebaseDatabase.getInstance().getReference();
    }

    private void ObtenerDatos(){
        String Uid_recuperado = getIntent().getStringExtra("Uid");
        String Correo_recuperado = getIntent().getStringExtra("Correo");

        Uid_Usuario.setText(Uid_recuperado);
        Correo_Usuario.setText(Correo_recuperado);
    }

    private void Obtener_Fecha_Hora_Actual(){
        String Fecha_hora_registro = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a",
                Locale.getDefault()).format(System.currentTimeMillis());
        //EJEMPLO: 13-11-2022/06:30:20 pm
        Fecha_hora_actual.setText();
    }

    private void Agregar_Nota(){

        //Obtener los datos
        String uid_usuario = Uid_Usuario.getText().toString();
        String correo_usuario = Correo_Usuario.getText().toString();
        String fecha_hora_actual = Fecha_hora_actual.getText().toString();
        String titulo = Titulo.getText().toString();
        String descripcion = Descripcion.getText().toString();
        String fecha = Fecha.getText().toString();
        String estado = Estado.getText().toString();

        //Validar datos
        if (!uid_usuario.equals("") && !correo_usuario.equals("") && !fecha_hora_actual.equals("") && !titulo.equals("") && !descripcion.equals("") && ! fecha.equals("") && !estado.equals("")){

            Nota nota = new Nota(correo_usuario + "/" + fecha_hora_actual, uid_usuario, correo_usuario, fecha_hora_actual, titulo, descripcion, fecha, estado);

            String Nota_usuario = BD_Firebase.push().getKey();
            //Establecer el nombre de la BD
            String Nombre_BD = "Notas_publicadas";

            BD_Firebase.child(Nombre_BD).child(Nota_usuario).setValue(nota);

            Toast.makeText(this, "Se ha agregado la nota exitosamente", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Llenar todos los campos", Toast-Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_agenda, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Agregar_Nota_BD:
                Toast.makeText(this, "Nota agregada con éxito", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp()
    }
}