package calendall.com.br.calendallpro.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Arrays;

import calendall.com.br.calendallpro.BuildConfig;
import calendall.com.br.calendallpro.R;
import calendall.com.br.calendallpro.dtoIN.CadastroUsuarioIN;
import calendall.com.br.calendallpro.dtoIN.RetornoIN;
import calendall.com.br.calendallpro.dtoOUT.CadastroUsuarioEnderecoOUT;
import calendall.com.br.calendallpro.dtoOUT.CadastroUsuarioOUT;
import calendall.com.br.calendallpro.service.CallService;
import calendall.com.br.calendallpro.service.CallServiceInterface;
import calendall.com.br.calendallpro.service.ServiceName;
import calendall.com.br.calendallpro.util.SharedUtil;
import calendall.com.br.calendallpro.util.Utils;

public class RegisterEnderecoActivity extends AppCompatActivity {

    EditText cpf;
    EditText celular;
    EditText cep;
    EditText numero;
    EditText complemento;
    CheckBox meuLocal;
    CheckBox domicilio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_endereco);

        cpf = (EditText) findViewById(R.id.cpf);
        celular = (EditText) findViewById(R.id.celular);
        cep = (EditText) findViewById(R.id.cep);
        numero = (EditText) findViewById(R.id.numero);
        complemento = (EditText) findViewById(R.id.complemento);
        meuLocal = (CheckBox) findViewById(R.id.meuLocal);
        domicilio = (CheckBox) findViewById(R.id.domicilio);
    }

    public void onContinuar(View v) {
        String cpf = this.cpf.getText().toString();
        String celular = this.celular.getText().toString();
        String cep = this.cep.getText().toString();
        String numero = this.numero.getText().toString();
        //String complemento = this.complemento.getText().toString();

        boolean valido = true;
        if (cpf.isEmpty()) {
            this.cpf.setError("Este campo é obrigatório!");
            valido = false;
        }

        if (celular.isEmpty()) {
            this.celular.setError("Este campo é obrigatório!");
            valido = false;
        }

        if (cep.isEmpty()) {
            this.cep.setError("Este campo é obrigatório!");
            valido = false;
        }

        if (numero.isEmpty()) {
            this.numero.setError("Este campo é obrigatório!");
            valido = false;
        }

        if (!meuLocal.isChecked() && !domicilio.isChecked()) {
            Toast.makeText(this, "Favor selecionar onde você atende!", Toast.LENGTH_SHORT).show();
            valido = false;
        }

        if (valido) {
            regitrarEndereco();
        }
    }

    private void regitrarEndereco() {
        final Gson gson = new Gson();
        CallService callService = new CallService(this, new CallServiceInterface() {
            @Override
            public void postCallService(String string) {

                if (BuildConfig.DEBUG)
                    Log.i("RET", string);

                RetornoIN in = gson.fromJson(string, RetornoIN.class);

                if (in.isOk()) {
                    Intent intent = new Intent(RegisterEnderecoActivity.this, RegisterBoletoCartaoActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterEnderecoActivity.this, "Não foi possivel cadastrar!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SharedUtil sharedUtil = new SharedUtil(this);
        Long id = Long.valueOf(sharedUtil.getPreferences(SharedUtil.KEY_ID));

        String tipoAtencimento = getTipoAtendimento();
        CadastroUsuarioEnderecoOUT out = new CadastroUsuarioEnderecoOUT(id, this.cpf.getText().toString(), this.celular.getText().toString(), this.cep.getText().toString(), Integer.valueOf(this.numero.getText().toString()), this.complemento.getText().toString(), tipoAtencimento);
        callService.execute(ServiceName.CADASTRO_USUARIO_ENDERECO, gson.toJson(out));
    }

    private String getTipoAtendimento() {
        String retorno = "I";

        if (meuLocal.isChecked() && domicilio.isChecked()) {
            retorno = "A";
        } else if (meuLocal.isChecked()) {
            retorno = "L";
        } else if (domicilio.isChecked()) {
            retorno = "R";
        }

        return retorno;
    }
}
