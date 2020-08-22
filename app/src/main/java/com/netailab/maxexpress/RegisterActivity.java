package com.netailab.maxexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.netailab.maxexpress.modelos.Usuario;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText nombre, correo, clave;
    Button registrar;
    AlertDialog mDialog;
    Toolbar toolbar;
    DatabaseReference mDatabaseReference;
    FirebaseAuth mAuth;
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nombre = findViewById(R.id.nombre);
        correo = findViewById(R.id.correo);
        clave = findViewById(R.id.clave);
        registrar = findViewById(R.id.registrar);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Por favor espere...").build();
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });

    }

    private void registrarUsuario() {
        final String inombre = nombre.getText().toString();
        final String icorreo = correo.getText().toString();
        String iclave = clave.getText().toString();

        if (!inombre.isEmpty() && !icorreo.isEmpty() && !iclave.isEmpty()) {
            if (iclave.length() >= 6) {
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(icorreo, iclave)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    guardarUsuarioDb(inombre, icorreo);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                                }
                                mDialog.dismiss();
                            }
                        });

            } else {
                Toast.makeText(this, "Las contraseña debe tener como minimo 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarUsuarioDb(String nombre, String correo) {
        String selectedUser = mPref.getString("user", "");
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);

        if (selectedUser.equals("cliente")) {
            mDatabaseReference.child("Usuarios").child("Clientes").push().setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if ((selectedUser.equals("conductor"))) {
            mDatabaseReference.child("Usuarios").child("Conductores").push().setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}