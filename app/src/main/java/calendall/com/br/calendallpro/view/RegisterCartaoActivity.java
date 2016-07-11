package calendall.com.br.calendallpro.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import calendall.com.br.calendallpro.BuildConfig;
import calendall.com.br.calendallpro.R;
import calendall.com.br.calendallpro.dtoIN.RetornoIN;
import calendall.com.br.calendallpro.dtoOUT.CadastroCartaoOUT;
import calendall.com.br.calendallpro.service.CallService;
import calendall.com.br.calendallpro.service.CallServiceInterface;
import calendall.com.br.calendallpro.service.ServiceName;
import calendall.com.br.calendallpro.util.SharedUtil;

public class RegisterCartaoActivity extends AppCompatActivity {

    EditText numero;
    EditText nome;
    EditText mes;
    EditText ano;
    EditText cvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cartao);

        numero = (EditText) findViewById(R.id.numero);
        nome = (EditText) findViewById(R.id.nome);
        mes = (EditText) findViewById(R.id.mes);
        ano = (EditText) findViewById(R.id.ano);
        cvv = (EditText) findViewById(R.id.cvv);
    }

    public void onContinuar(View v) {
        String numero = this.numero.getText().toString();
        String nome = this.nome.getText().toString();
        String mes = this.mes.getText().toString();
        String ano = this.ano.getText().toString();
        String cvv = this.cvv.getText().toString();

        boolean valido = true;
        if (numero.isEmpty()) {
            this.numero.setError("Este campo é obrigatório!");
            valido = false;
        }

        if (nome.isEmpty()) {
            this.nome.setError("Este campo é obrigatório!");
            valido = false;
        }

        if (mes.isEmpty()) {
            this.mes.setError("Este campo é obrigatório!");
            valido = false;
        }

        if (ano.isEmpty()) {
            this.ano.setError("Este campo é obrigatório!");
            valido = false;
        }

        if (cvv.isEmpty()) {
            this.cvv.setError("Este campo é obrigatório!");
            valido = false;
        }

        if (valido) {
            regitrarCartao(numero, nome, mes, ano, cvv);
        }
    }

    private void regitrarCartao(String numero, String nome, String mes, String ano, String cvv) {
        final Gson gson = new Gson();
        CallService callService = new CallService(this, new CallServiceInterface() {
            @Override
            public void postCallService(String string) {

                if (BuildConfig.DEBUG)
                    Log.i("RET", string);

                RetornoIN in = gson.fromJson(string, RetornoIN.class);

                if (in.isOk()) {
                    Intent intent = new Intent(RegisterCartaoActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterCartaoActivity.this, "Não foi possivel cadastrar!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SharedUtil sharedUtil = new SharedUtil(this);
        Long id = Long.valueOf(sharedUtil.getPreferences(SharedUtil.KEY_ID));

        CadastroCartaoOUT out = new CadastroCartaoOUT(id, numero, nome, cvv, Integer.valueOf(mes), Integer.valueOf(ano));
        callService.execute(ServiceName.CADASTRO_USUARIO_CARTAO, gson.toJson(out));
    }

}
