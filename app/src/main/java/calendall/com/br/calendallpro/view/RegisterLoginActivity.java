package calendall.com.br.calendallpro.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import calendall.com.br.calendallpro.dtoIN.LoginIN;
import calendall.com.br.calendallpro.dtoOUT.CadastroUsuarioOUT;
import calendall.com.br.calendallpro.dtoOUT.LoginOUT;
import calendall.com.br.calendallpro.service.CallService;
import calendall.com.br.calendallpro.service.CallServiceInterface;
import calendall.com.br.calendallpro.service.ServiceName;
import calendall.com.br.calendallpro.util.SharedUtil;
import calendall.com.br.calendallpro.util.Utils;

public class RegisterLoginActivity extends AppCompatActivity {

    EditText nome;
    EditText email;
    EditText senha;

    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_register_login);

        nome = (EditText) findViewById(R.id.nome);
        email = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest( loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        String nome = object.optString("name");
                        String email = object.optString("email");
                        String id = object.optString("id");

                        if (BuildConfig.DEBUG) {
                            String asd = String.format("%s %s %s", nome, email, id);
                            Log.i("FACE", asd);
                        }

                        regitrarLogin(nome, email, id);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException error) {}
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onContinuar(View v) {
        String nome = this.nome.getText().toString();
        String email = this.email.getText().toString();
        String senha = this.senha.getText().toString();

        boolean valido = true;
        if (nome.isEmpty()) {
            this.nome.setError("Este campo é obrigatório!");
            valido = false;
        }

        if (!(Utils.isEmailValid(email))) {
            this.email.setError("Email inválido!");
            valido = false;
        }

        if (senha.length() < 6) {
            this.senha.setError("Deve conter pelo menos 6 digitos!");
            valido = false;
        }

        if (senha.length() > 20) {
            this.senha.setError("Deve conter no maximo 20 digitos!");
            valido = false;
        }

        if (valido) {
            regitrarLogin(nome, email, senha);
        }
    }

    private void regitrarLogin(final String nome, final String email, final String senha) {
        final Gson gson = new Gson();
        CallService callService = new CallService(this, new CallServiceInterface() {
            @Override
            public void postCallService(String string) {

                if (BuildConfig.DEBUG)
                    Log.i("RET", string);

                CadastroUsuarioIN in = gson.fromJson(string, CadastroUsuarioIN.class);

                if (in.isOk()) {
                    SharedUtil sharedUtil = new SharedUtil(RegisterLoginActivity.this);
                    sharedUtil.setPreferences(SharedUtil.KEY_ID, String.valueOf(in.getId()));
                    sharedUtil.setPreferences(SharedUtil.KEY_NOME, nome);
                    sharedUtil.setPreferences(SharedUtil.KEY_EMAIL, email);
                    sharedUtil.setPreferences(SharedUtil.KEY_SENHA, senha);

                    Intent intent = new Intent(RegisterLoginActivity.this, RegisterEnderecoActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(RegisterLoginActivity.this, "Não foi possivel cadastrar!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        CadastroUsuarioOUT out = new CadastroUsuarioOUT(nome, email, senha);
        callService.execute(ServiceName.CADASTRO_USUARIO, gson.toJson(out));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }

}
