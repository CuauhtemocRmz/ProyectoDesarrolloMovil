package com.example.agenda_10b;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class Recuperar_Contraseña extends AppCompatActivity {

    FirebaseAuth auth;

    Button Recuperarcontraseña;
    EditText CorreoRecupear;
    ProgressDialog progressDialog;
    String correo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena_olvidada);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere por favor ...");
        progressDialog.setCanceledOnTouchOutside(false);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        Recuperarcontraseña = findViewById(R.id.Btn_RecuperarContraseña);
        CorreoRecupear = findViewById(R.id.CorreoRecupearET);






        Recuperarcontraseña.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Recuperar contraseña")
                    .setMessage("¿Quieres recuperar la contraseña de: "
                            +CorreoRecupear.getText() + "?")
                    .setPositiveButton("Recuperar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            correo = CorreoRecupear.getText().toString();

                            auth.sendPasswordResetEmail(correo)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            //Envío fue exitoso
                                            progressDialog.dismiss();
                                            Toast.makeText(Recuperar_Contraseña.this, "Correo enviado, revise su bandeja " +CorreoRecupear.getText(), Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //Falló el envío
                                            Toast.makeText(Recuperar_Contraseña.this, "Falló debido a: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(Recuperar_Contraseña.this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }).show();

        });
    }

}
