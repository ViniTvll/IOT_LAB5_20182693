package com.example.iot_lab5_20182693;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NuevaHabitoActivity extends AppCompatActivity {

    private EditText etNombreHabito, etFrecuencia;
    private Spinner spinnerCategoria;
    private Button btnFechaHora, btnGuardarHabito;
    private TextView tvFechaHora;
    private Calendar calendar;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private List<Habito> listaHabitos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_habito);

        etNombreHabito = findViewById(R.id.etNombreHabito);
        etFrecuencia = findViewById(R.id.etFrecuencia);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        btnFechaHora = findViewById(R.id.btnFechaHora);
        btnGuardarHabito = findViewById(R.id.btnGuardarHabito);
        tvFechaHora = findViewById(R.id.tvFechaHora);

        sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        gson = new Gson();
        listaHabitos = cargarHabitos();

        // Configurar Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Ejercicio", "Alimentación", "Sueño", "Lectura"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        calendar = Calendar.getInstance();

        btnFechaHora.setOnClickListener(v -> mostrarDateTimePicker());

        btnGuardarHabito.setOnClickListener(v -> guardarHabito());
    }

    private void mostrarDateTimePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                            (view1, hourOfDay, minute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                tvFechaHora.setText("Inicio: " + new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(calendar.getTime()));
                            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                    timePickerDialog.show();
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private List<Habito> cargarHabitos() {
        String json = sharedPreferences.getString("listaHabitos", null);
        if (json == null) {
            return new ArrayList<>();
        } else {
            Type tipoLista = new TypeToken<List<Habito>>(){}.getType();
            return gson.fromJson(json, tipoLista);
        }
    }

    private void guardarHabito() {
        String nombre = etNombreHabito.getText().toString();
        String categoria = spinnerCategoria.getSelectedItem().toString();
        int frecuencia = 0;
        try {
            frecuencia = Integer.parseInt(etFrecuencia.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String fechaHoraInicio = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(calendar.getTime());

        Habito habito = new Habito(nombre, categoria, frecuencia, fechaHoraInicio);
        listaHabitos.add(habito);

        String json = gson.toJson(listaHabitos);
        sharedPreferences.edit().putString("listaHabitos", json).apply();

        Toast.makeText(this, "Hábito guardado", Toast.LENGTH_SHORT).show();
        finish(); // Volver a la lista de hábitos
    }
}

