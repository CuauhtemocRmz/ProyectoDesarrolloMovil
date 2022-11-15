package com.example.agenda_10b.notas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.agenda_10b.Objetos.Nota;
import com.example.agenda_10b.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Agregar_Nota extends AppCompatActivity {

    TextView Uid_Usuario, Correo_usuario, Fecha_hora_actual, Fecha, Hora_Seleccionada, Estado;
    EditText Titulo, Descripcion;
    Button Btn_Calendario, Btn_Hora;

    int dia, mes , año, hora, minutos;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    DatabaseReference BD_Firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        InicializarVariables();
        ObtenerDatos();
        Obtener_Fecha_Hora_Actual();

        Btn_Calendario.setOnClickListener(view -> {
            final Calendar calendario = Calendar.getInstance();

            dia = calendario.get(Calendar.DAY_OF_MONTH);
            mes = calendario.get(Calendar.MONTH);
            año = calendario.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(Agregar_Nota.this, (datePicker, AnioSeleccionado, MesSeleccionado, DiaSeleccionado) -> {

                String diaFormateado, mesFormateado;

                //OBTENER DIA
                if (DiaSeleccionado < 10){
                    diaFormateado = "0"+ DiaSeleccionado;
                    // Antes: 9/11/2022 -  Ahora 09/11/2022
                }else {
                    diaFormateado = String.valueOf(DiaSeleccionado);
                    //Ejemplo 13/08/2022
                }

                //OBTENER EL MES
                int Mes = MesSeleccionado + 1;

                if (Mes < 10){
                    mesFormateado = "0"+ Mes;
                    // Antes: 09/8/2022 -  Ahora 09/08/2022
                }else {
                    mesFormateado = String.valueOf(Mes);
                    //Ejemplo 13/10/2022 - 13/11/2022 - 13/12/2022

                }

                //Setear fecha en TextView
                Fecha.setText(diaFormateado + "/" + mesFormateado + "/"+ AnioSeleccionado);

            }
                    ,año,mes,dia);
            datePickerDialog.show();

        });

        Btn_Hora.setOnClickListener(view ->{
            final Calendar calendario = Calendar.getInstance();
            hora = calendario.get(Calendar.HOUR_OF_DAY);
            minutos = calendario.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(Agregar_Nota.this, (timePicker, HoraSeleccionada, MinutosSeleccionados) -> {

                int Hora = HoraSeleccionada;
                int Minutos = MinutosSeleccionados;

                String MinutosFormateado;

                if(Minutos < 10){
                    MinutosFormateado = "0"+ Minutos;
                }else{
                    MinutosFormateado = String.valueOf(Minutos);
                }

                Hora_Seleccionada.setText(Hora + ":" + MinutosFormateado);
                    },hora, minutos, false);
            timePickerDialog.show();

        });
    }

    private void InicializarVariables(){
        Uid_Usuario = findViewById(R.id.Uid_Usuario);
        Correo_usuario = findViewById(R.id.Correo_Usuario);
        Fecha_hora_actual = findViewById(R.id.Fecha_hora_actual);
        Fecha = findViewById(R.id.Fecha);
        Hora_Seleccionada = findViewById(R.id.Hora_Seleccionada);
        Estado = findViewById(R.id.Estado);

        Titulo = findViewById(R.id.Titulo);
        Descripcion = findViewById(R.id.Descripcion);
        Btn_Calendario = findViewById(R.id.Btn_Calendario);
        Btn_Hora =findViewById(R.id.Btn_Hora);

        BD_Firebase = FirebaseDatabase.getInstance().getReference("Usuarios");

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    private void ObtenerDatos(){
        String uid_recuperado = getIntent().getStringExtra("Uid");
        String correo_recuperado = getIntent().getStringExtra("Correo");

        Uid_Usuario.setText(uid_recuperado);
        Correo_usuario.setText(correo_recuperado);
    }

    private void Obtener_Fecha_Hora_Actual(){
        String Fecha_hora_registro = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a",
                Locale.getDefault()).format(System.currentTimeMillis());
        //EJEMPLO: 13-11-2022/06:30:20 pm
        Fecha_hora_actual.setText(Fecha_hora_registro);
    }

    @SuppressLint("NotConstructor")
    private void Agregar_Nota(){

        //Obtener los datos
        String uid_usuario = Uid_Usuario.getText().toString();
        String correo_usuario = Correo_usuario.getText().toString();
        String fecha_hora_actual = Fecha_hora_actual.getText().toString();
        String titulo = Titulo.getText().toString();
        String descripcion = Descripcion.getText().toString();
        String fecha = Fecha.getText().toString();
        String hora = Hora_Seleccionada.getText().toString();
        String estado = Estado.getText().toString();
        String id_nota = BD_Firebase.push().getKey();

        //Validar datos
        if (!uid_usuario.equals("") && !correo_usuario.equals("") && !fecha_hora_actual.equals("") &&
                !titulo.equals("") && !descripcion.equals("") && ! fecha.equals("") && ! hora.equals("") && !estado.equals("")){

            Nota nota = new Nota(id_nota,
                    uid_usuario,
                    correo_usuario,
                    fecha_hora_actual,
                    titulo,
                    descripcion,
                    fecha,
                    hora,
                    estado);

            //Establecer el nombre de la BD
            String Nombre_BD = "Notas_Publicadas";

            assert id_nota != null;
            BD_Firebase.child(user.getUid()).child(Nombre_BD).child(id_nota).setValue(nota);

            Toast.makeText(this, "Se ha agregado la nota exitosamente", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        else {
            Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_agregar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Agregar_Nota_BD) {
            Agregar_Nota();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}