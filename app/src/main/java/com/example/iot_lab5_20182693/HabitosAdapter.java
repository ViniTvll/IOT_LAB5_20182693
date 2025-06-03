package com.example.iot_lab5_20182693;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HabitosAdapter extends RecyclerView.Adapter<HabitosAdapter.HabitoViewHolder> {

    private List<Habito> listaHabitos;

    public HabitosAdapter(List<Habito> listaHabitos) {
        this.listaHabitos = listaHabitos;
    }

    @NonNull
    @Override
    public HabitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habito, parent, false);
        return new HabitoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitoViewHolder holder, int position) {
        Habito habito = listaHabitos.get(position);
        holder.tvNombre.setText(habito.getNombre());
        holder.tvCategoria.setText("Categoría: " + habito.getCategoria());
        holder.tvFrecuencia.setText("Frecuencia: cada " + habito.getFrecuenciaHoras() + " horas");
        holder.tvFechaHora.setText("Inicio: " + habito.getFechaHoraInicio());
    }

    @Override
    public int getItemCount() {
        return listaHabitos.size();
    }

    public static class HabitoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCategoria, tvFrecuencia, tvFechaHora;

        public HabitoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCategoria = itemView.findViewById(R.id.tvCategoria);
            tvFrecuencia = itemView.findViewById(R.id.tvFrecuencia);
            tvFechaHora = itemView.findViewById(R.id.tvFechaHora);
        }
    }
}

