package com.netailab.maxexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnConductor, btnCliente;
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
        final SharedPreferences.Editor editor = mPref.edit();

        btnConductor = findViewById(R.id.soy_conductor);
        btnCliente = findViewById(R.id.soy_cliente);

        /*Boton para seleccionar Chofer*/
        btnConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user","conductor");
                editor.apply();
                navegarVistaAutenticacion();
            }
        });

        /*Boton para seleccionar Cliente*/
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user","cliente");
                editor.apply();
                navegarVistaAutenticacion();
            }
        });
    }

    /*Navegar a la vista de Autenticacion de usuario*/
    private void navegarVistaAutenticacion() {
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);
    }
}