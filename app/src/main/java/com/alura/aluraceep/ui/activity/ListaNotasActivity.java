package com.alura.aluraceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alura.aluraceep.R;
import com.alura.aluraceep.ui.dao.NotaDAO;
import com.alura.aluraceep.ui.model.Nota;
import com.alura.aluraceep.ui.recyclerview.ListaNotasAdapter;

import java.util.List;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;
    private List<Nota> todasNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        todasNotas = notasDeExemplo();
        configuraRecyclerView(todasNotas);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == 2) {
                        Intent data = result.getData();
                        Nota notaRecebida = (Nota) data.getSerializableExtra("nota");
                        new NotaDAO().insere(notaRecebida);
                        adapter.adiciona(notaRecebida);
                    }
                });

        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormularioNotaActivity.class);
            someActivityResultLauncher.launch(intent);
        });
    }


    private List<Nota> notasDeExemplo() {
        NotaDAO notaDAO = new NotaDAO();
//        for (int i = 0; i < 10000; i++) {
//            notaDAO.insere(new Nota("Nota " + i, "descrição " + i));
//        }
        notaDAO.insere(new Nota("Primeira Nota", "Primeira descrição"));
        notaDAO.insere(new Nota("Segunda Nota", "Segunda descrição"));
        List<Nota> todasNotas = notaDAO.todos();
        return todasNotas;
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(adapter);
    }
}