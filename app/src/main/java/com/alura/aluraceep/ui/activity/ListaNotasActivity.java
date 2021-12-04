package com.alura.aluraceep.ui.activity;

import static com.alura.aluraceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static com.alura.aluraceep.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alura.aluraceep.R;
import com.alura.aluraceep.ui.dao.NotaDAO;
import com.alura.aluraceep.ui.model.Nota;
import com.alura.aluraceep.ui.recyclerview.ListaNotasAdapter;

import java.util.List;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        List<Nota> todasNotas = pegaTodasNotas();
        configuraRecyclerView(todasNotas);

        ActivityResultLauncher<Intent> notaCriadaPeloFormulario = recebeNotaCriada();

        configuraBotaoInsereNota(notaCriadaPeloFormulario);
    }

    @NonNull
    private ActivityResultLauncher<Intent> recebeNotaCriada() {
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    if (ehResultadoComNota(result, data)) {
                        Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                        adiciona(notaRecebida);
                    }
                });
        return someActivityResultLauncher;
    }

    private void adiciona(Nota nota) {
        new NotaDAO().insere(nota);
        adapter.adiciona(nota);
    }

    private boolean ehResultadoComNota(androidx.activity.result.ActivityResult result, Intent data) {
        return ehCodigoResultadoNotaCriada(result) && temNota(data);
    }

    private boolean temNota(Intent data) {
        return data.hasExtra(CHAVE_NOTA);
    }

    private boolean ehCodigoResultadoNotaCriada(androidx.activity.result.ActivityResult result) {
        return result.getResultCode() == CODIGO_RESULTADO_NOTA_CRIADA;
    }

    private void configuraBotaoInsereNota(ActivityResultLauncher<Intent> someActivityResultLauncher) {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(v -> {
            vaiParaFormularioNotaActivity(someActivityResultLauncher);
        });
    }

    private void vaiParaFormularioNotaActivity(ActivityResultLauncher<Intent> someActivityResultLauncher) {
        Intent intent = new Intent(this, FormularioNotaActivity.class);
        someActivityResultLauncher.launch(intent);
    }

    private List<Nota> pegaTodasNotas() {
        NotaDAO notaDAO = new NotaDAO();
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