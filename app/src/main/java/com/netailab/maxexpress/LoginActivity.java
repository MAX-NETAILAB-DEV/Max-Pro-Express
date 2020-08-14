package com.netailab.maxexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText input_correo, input_clave;
    Button btn_login;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        input_correo = findViewById(R.id.input_correo);
        input_clave = findViewById(R.id.input_clave);
        btn_login = findViewById(R.id.btn_ingresar);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });


    }

    private void login() {
        String email = input_correo.getText().toString();
        String clave = input_clave.getText().toString();

        if (!email.isEmpty() && !clave.isEmpty()) {
            if (clave.length() >= 6) {
                mAuth.signInWithEmailAndPassword(email, clave)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginActivity.this, "exito", Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "algo salio mal", Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            } else {
                Toast.makeText(LoginActivity.this, "La contraseña debe tener minino 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "El correo y la contraseña son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }

}