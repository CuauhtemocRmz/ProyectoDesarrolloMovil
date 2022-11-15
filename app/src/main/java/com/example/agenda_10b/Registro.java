package com.example.agenda_10b;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {

    EditText NombreEt,ApellidoEt,CorreoEt, ConfirmarCorreo,ContaseñaEt,ConfirmarContraseñaEt;
    Button RegistrarUsuario;
    TextView TengounacuentaTXT;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    String nombres = " " , apellidos = "", correo = " ", confirmaremail = "" , password = "" , confirmarpassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registrar");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        NombreEt = findViewById(R.id.NombreEt);
        ApellidoEt = findViewById(R.id.ApellidoEt);
        CorreoEt = findViewById(R.id.CorreoEt);
        ConfirmarCorreo = findViewById(R.id.ConfirmarCorreoEt);
        ContaseñaEt = findViewById(R.id.ContraseñaEt);
        ConfirmarContraseñaEt = findViewById(R.id.ConfirmarContraseñaEt);
        RegistrarUsuario = findViewById(R.id.RegistrarUsuario);
        TengounacuentaTXT = findViewById(R.id.TengounacuentaTXT);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Registro.this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        RegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidarDatos();
            }
        });

        TengounacuentaTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registro.this, Login.class));
            }
        });
    }

    private void ValidarDatos(){
        nombres = NombreEt.getText().toString();
        apellidos = ApellidoEt.getText().toString();
        correo = CorreoEt.getText().toString();
        confirmaremail = ConfirmarCorreo.getText().toString();
        password = ContaseñaEt.getText().toString();
        confirmarpassword = ConfirmarContraseñaEt.getText().toString();

        if (TextUtils.isEmpty(nombres)){
            Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show();
        }else if(!nombres.matches("^[\\p{L} .'-]+$")){
            Toast.makeText(this, "Ingrese un nombre valido", Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(apellidos)){
            Toast.makeText(this, "Ingrese apellidos", Toast.LENGTH_SHORT).show();
        }
        else if(!apellidos.matches("^[\\p{L} .'-]+$")){
            Toast.makeText(this, "Ingrese apellidos validos", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Ingrese correo", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(confirmaremail)){
            Toast.makeText(this, "Confirme correo", Toast.LENGTH_SHORT).show();
        }
        else if (!correo.equals(confirmaremail)){
            Toast.makeText(this, "Los correos no coinciden", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(confirmarpassword)){
            Toast.makeText(this, "Confirme contraseña", Toast.LENGTH_SHORT).show();

        }
        else if (!password.equals(confirmarpassword)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        else {
            CrearCuenta();
        }
    }

    private void CrearCuenta() {
        progressDialog.setMessage("Creando su cuenta...");
        progressDialog.show();

        //Crear un usuario en Firebase
        firebaseAuth.createUserWithEmailAndPassword(correo, password)
                .addOnSuccessListener(authResult -> GuardarInformacion()).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(Registro.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }

    private void GuardarInformacion() {
        progressDialog.setMessage("Guardando su información");
        progressDialog.dismiss();

        //Obtener la identificación de usuario actual
        String uid = firebaseAuth.getUid();

        HashMap<String, String> Datos = new HashMap<>();
        Datos.put("uid",  uid);
        Datos.put("correo", correo);
        Datos.put("nombres", nombres);
        Datos.put("apellidos",apellidos);
        Datos.put("password", password);

        Datos.put("telefono","");
        Datos.put("domicilio","");
        Datos.put("universidad","");
        Datos.put("fecha_de_nacimiento", "");
        Datos.put("imagen_perfil","");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(uid)
                .setValue(Datos)
                .addOnSuccessListener(unused -> {
                    progressDialog.dismiss();
                    Toast.makeText(Registro.this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Registro.this, MenuPrincipal.class));
                    finish();
                }).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(Registro.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}