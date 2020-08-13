package com.netailab.maxexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnConductor,btnCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConductor = findViewById(R.id.soy_conductor);
        btnConductor = findViewById(R.id.soy_conductor);

        /*Boton para seleccionar Chofer*/
        btnConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /*Boton para seleccionar Cliente*/
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}