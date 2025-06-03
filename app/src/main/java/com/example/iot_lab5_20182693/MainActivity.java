package com.example.iot_lab5_20182693;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView tvSaludo, tvMotivacion;
    private ImageView ivImagen;
    private Button btnVerHabitos, btnConfiguracion;
    private SharedPreferences sharedPreferences;
    private static final int REQUEST_CODE_PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSaludo = findViewById(R.id.tvSaludo);
        tvMotivacion = findViewById(R.id.tvMotivacion);
        ivImagen = findViewById(R.id.ivImagen);
        btnVerHabitos = findViewById(R.id.btnVerHabitos);
        btnConfiguracion = findViewById(R.id.btnConfiguracion);

        sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        cargarDatos();

        ivImagen.setOnClickListener(v -> abrirGaleria());

        btnVerHabitos.setOnClickListener(v -> {
            // Ir a pantalla de hábitos
            startActivity(new Intent(this, HabitosActivity.class));
        });

        btnConfiguracion.setOnClickListener(v -> {
            // Ir a pantalla de configuraciones
            startActivity(new Intent(this, ConfiguracionActivity.class));
        });
    }

    private void cargarDatos() {
        String nombre = sharedPreferences.getString("nombreUsuario", "Usuario");
        String mensaje = sharedPreferences.getString("mensajeMotivacional", "Aquí va tu mensaje motivacional");
        tvSaludo.setText("¡Hola, " + nombre + "!");
        tvMotivacion.setText(mensaje);

        try {
            File file = new File(getFilesDir(), "imagen.jpg");
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                ivImagen.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ivImagen.setImageBitmap(bitmap);

                // Guardar imagen en almacenamiento interno
                File file = new File(getFilesDir(), "imagen.jpg");
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}