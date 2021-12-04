package com.alura.aluraceep.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.alura.aluraceep.R;
import com.alura.aluraceep.ui.dao.NotaDAO;
import com.alura.aluraceep.ui.model.Nota;
import com.alura.aluraceep.ui.recyclerview.ListaNotasAdapter;

import java.util.List;

public class ListaNotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        List<Nota> todasNotas = notasDeExemplo();
        configuraRecyclerView(todasNotas);

        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormularioNotaActivity.class);
            startActivity(intent);
        });
    }

    private List<Nota> notasDeExemplo() {
        NotaDAO notaDAO = new NotaDAO();
        for (int i = 0; i < 10000; i++) {
            notaDAO.insere(new Nota("Nota " + i, "descrição " + 1));
        }
        List<Nota> todasNotas = notaDAO.todos();
        return todasNotas;
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        listaNotas.setAdapter(new ListaNotasAdapter(this, todasNotas));
    }
}