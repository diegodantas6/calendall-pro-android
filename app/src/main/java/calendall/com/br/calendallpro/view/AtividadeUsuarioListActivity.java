package calendall.com.br.calendallpro.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import calendall.com.br.calendallpro.R;
import calendall.com.br.calendallpro.dtoIN.LoginIN;
import calendall.com.br.calendallpro.dtoIN.RetornoIN;
import calendall.com.br.calendallpro.dtoIN.UsuarioAtividadeIN;
import calendall.com.br.calendallpro.dtoOUT.AtividadeOUT;
import calendall.com.br.calendallpro.dtoOUT.DadoAtividadeOUT;
import calendall.com.br.calendallpro.dtoOUT.UsuarioAtividadeOUT;
import calendall.com.br.calendallpro.list.AtividadeAdapter;
import calendall.com.br.calendallpro.list.AtividadeUsuarioAdapter;
import calendall.com.br.calendallpro.service.CallService;
import calendall.com.br.calendallpro.service.CallServiceInterface;
import calendall.com.br.calendallpro.service.ServiceName;
import calendall.com.br.calendallpro.util.SharedUtil;

public class AtividadeUsuarioListActivity extends AppCompatActivity {

    ListView mylistview;
    UsuarioAtividadeIN[] atividades;
    AtividadeUsuarioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_usuario_list);

        String dados = getIntent().getExtras().getString("DADOS");
        Gson gson = new Gson();
        atividades = gson.fromJson(dados, UsuarioAtividadeIN[].class);

        mylistview = (ListView) findViewById(R.id.list);
        adapter = new AtividadeUsuarioAdapter(this, atividades);
        mylistview.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mylistview.requestFocus();

                List<DadoAtividadeOUT> dados = new ArrayList<DadoAtividadeOUT>();

                for (UsuarioAtividadeIN atividade : atividades) {
                    DadoAtividadeOUT dado = new DadoAtividadeOUT(atividade.getId(), atividade.getTempo(), atividade.getPreco());
                    dados.add(dado);
                }

                final Gson gson = new Gson();
                String json = gson.toJson(dados);

                CallService callService = new CallService(AtividadeUsuarioListActivity.this, new CallServiceInterface() {
                    @Override
                    public void postCallService(String string) {
                        RetornoIN in = gson.fromJson(string, RetornoIN.class);

                        if (in.isOk()) {
                            Intent intent = new Intent(AtividadeUsuarioListActivity.this, MenuActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AtividadeUsuarioListActivity.this, "Deu algum erro!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                callService.execute(ServiceName.DADO_ATIVIDADES, json);
            }
        });
    }

}
