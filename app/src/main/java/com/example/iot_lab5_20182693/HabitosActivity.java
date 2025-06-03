package com.example.iot_lab5_20182693;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HabitosActivity extends AppCompatActivity {

    private RecyclerView rvHabitos;
    private FloatingActionButton fabAgregar;
    private List<Habito> listaHabitos;
    private HabitosAdapter adapter;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitos);

        rvHabitos = findViewById(R.id.rvHabitos);
        fabAgregar = findViewById(R.id.fabAgregar);

        sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        gson = new Gson();

        listaHabitos = cargarHabitos();
        adapter = new HabitosAdapter(listaHabitos);
        rvHabitos.setLayoutManager(new LinearLayoutManager(this));
        rvHabitos.setAdapter(adapter);

        fabAgregar.setOnClickListener(v -> {
            startActivity(new Intent(this, NuevaHabitoActivity.class));
        });
    }

    private List<Habito> cargarHabitos() {
        String json = sharedPreferences.getString("listaHabitos", null);
        if (json == null) {
            Toast.makeText(this, "No hay hábitos registrados", Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        } else {
            Type tipoLista = new TypeToken<List<Habito>>(){}.getType();
            return gson.fromJson(json, tipoLista);
        }
    }
}
