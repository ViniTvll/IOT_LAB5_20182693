package com.example.iot_lab5_20182693;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfiguracionActivity extends AppCompatActivity {

    private EditText etNombre, etMensaje, etFrecuencia;
    private Button btnGuardar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        etNombre = findViewById(R.id.etNombre);
        etMensaje = findViewById(R.id.etMensaje);
        etFrecuencia = findViewById(R.id.etFrecuencia);
        btnGuardar = findViewById(R.id.btnGuardar);

        sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        cargarDatos();

        btnGuardar.setOnClickListener(v -> guardarDatos());
    }

    private void cargarDatos() {
        String nombre = sharedPreferences.getString("nombreUsuario", "");
        String mensaje = sharedPreferences.getString("mensajeMotivacional", "");
        int frecuencia = sharedPreferences.getInt("frecuenciaMotivacional", 0);

        etNombre.setText(nombre);
        etMensaje.setText(mensaje);
        etFrecuencia.setText(frecuencia > 0 ? String.valueOf(frecuencia) : "");
    }

    private void guardarDatos() {
        String nombre = etNombre.getText().toString();
        String mensaje = etMensaje.getText().toString();
        int frecuencia = 0;
        try {
            frecuencia = Integer.parseInt(etFrecuencia.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nombreUsuario", nombre);
        editor.putString("mensajeMotivacional", mensaje);
        editor.putInt("frecuenciaMotivacional", frecuencia);
        editor.apply();

        Toast.makeText(this, "Configuración guardada", Toast.LENGTH_SHORT).show();
        finish(); // Regresar a MainActivity
    }
}

