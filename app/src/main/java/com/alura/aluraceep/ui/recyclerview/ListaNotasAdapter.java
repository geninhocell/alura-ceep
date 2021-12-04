package com.alura.aluraceep.ui.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alura.aluraceep.R;
import com.alura.aluraceep.ui.model.Nota;

import java.util.List;

public class ListaNotasAdapter extends RecyclerView.Adapter{

    private List<Nota> notas;
    private Context context;
    private static int quantidadeViewCriada = 0;
    private static int quantidadeBindView = 0;

    public ListaNotasAdapter(Context context, List<Nota> notas){
        this.context = context;
        this.notas = notas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false);
        quantidadeViewCriada++;
        Log.i("recyclerView adapter ", "view holder criada: " + quantidadeViewCriada);
        return new NotaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Nota nota = notas.get(position);
        TextView titulo = holder.itemView.findViewById(R.id.item_nota_titulo);
        titulo.setText(nota.getTitulo());

        TextView descricao = holder.itemView.findViewById(R.id.item_nota_descricao);
        descricao.setText(nota.getDescricao());

        quantidadeBindView++;
        Log.i("recyclerView Adapter", "bind view holder: posição" + position + " quantidade " + quantidadeBindView);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
