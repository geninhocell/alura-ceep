package com.alura.aluraceep.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

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

        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);

        NotaDAO notaDAO = new NotaDAO();
        for (int i = 0; i < 10000; i++) {
            notaDAO.insere(new Nota("Nota " + i, "descrição " + 1));
        }
        List<Nota> todasNotas = notaDAO.todos();

        listaNotas.setAdapter(new ListaNotasAdapter(this, todasNotas));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listaNotas.setLayoutManager(linearLayoutManager);
    }
}