package calendall.com.br.calendallpro.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import calendall.com.br.calendallpro.R;
import calendall.com.br.calendallpro.dtoIN.LoginIN;
import calendall.com.br.calendallpro.dtoIN.RetornoIN;
import calendall.com.br.calendallpro.dtoOUT.LoginOUT;
import calendall.com.br.calendallpro.dtoOUT.RecuperarSenhaOUT;
import calendall.com.br.calendallpro.service.CallService;
import calendall.com.br.calendallpro.service.CallServiceInterface;
import calendall.com.br.calendallpro.service.ServiceName;
import calendall.com.br.calendallpro.util.SharedUtil;
import calendall.com.br.calendallpro.util.Utils;

public class EsqueciSenhaActivity extends AppCompatActivity {

    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        email = (EditText) findViewById(R.id.email);
    }

    public void onEnviar(View v) {
        String email = this.email.getText().toString();

        boolean valido = true;
        if (!(Utils.isEmailValid(email))) {
            this.email.setError("Email inválido!");
            valido = false;
        }

        if (valido) {
            recuperaSenha(email);
        }
    }

    private void recuperaSenha(String email) {
        final Gson gson = new Gson();
        CallService callService = new CallService(this, new CallServiceInterface() {
            @Override
            public void postCallService(String string) {
                RetornoIN in = gson.fromJson(string, RetornoIN.class);

                if (in.isOk()) {
                    Intent intent = new Intent(EsqueciSenhaActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(EsqueciSenhaActivity.this, "Foi enviada uma nova senha para seu email!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EsqueciSenhaActivity.this, "Usuário não encontrado!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        RecuperarSenhaOUT out = new RecuperarSenhaOUT(email);
        callService.execute(ServiceName.RECUPERAR_SENHA, gson.toJson(out));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
