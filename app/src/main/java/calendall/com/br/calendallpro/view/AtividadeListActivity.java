package calendall.com.br.calendallpro.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import calendall.com.br.calendallpro.R;
import calendall.com.br.calendallpro.dtoOUT.AtividadeOUT;
import calendall.com.br.calendallpro.dtoOUT.UsuarioAtividadeOUT;
import calendall.com.br.calendallpro.list.AtividadeAdapter;
import calendall.com.br.calendallpro.service.CallService;
import calendall.com.br.calendallpro.service.CallServiceInterface;
import calendall.com.br.calendallpro.service.ServiceName;

public class AtividadeListActivity extends AppCompatActivity {

    ListView mylistview;
    AtividadeOUT[] atividades;
    AtividadeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long usuario = 1L;
                List<Long> ativis = new ArrayList<Long>();
                for (AtividadeOUT atividadeOUT : atividades) {
                    if (atividadeOUT.isChecked()) {
                        ativis.add(atividadeOUT.getId());
                    }
                }

                UsuarioAtividadeOUT out = new UsuarioAtividadeOUT(usuario, ativis);
                final Gson gson = new Gson();
                String json = gson.toJson(out);

                CallService callService = new CallService(AtividadeListActivity.this, new CallServiceInterface() {
                    @Override
                    public void postCallService(String string) {
                        Intent intent = new Intent(AtividadeListActivity.this, AtividadeUsuarioListActivity.class);
                        intent.putExtra("DADOS", string);
                        startActivity(intent);
                    }
                });

                callService.execute(ServiceName.USUARIO_ATIVIDADES, json);
            }
        });

        CallService callService = new CallService(this, new CallServiceInterface() {
            @Override
            public void postCallService(String string) {
                Gson gson = new Gson();
                atividades = gson.fromJson(string, AtividadeOUT[].class);

                mylistview = (ListView) findViewById(R.id.list);
                adapter = new AtividadeAdapter(AtividadeListActivity.this, atividades);
                mylistview.setAdapter(adapter);
            }
        });

        callService.execute(ServiceName.ATIVIDADES, null);
    }

}
