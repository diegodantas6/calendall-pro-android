package calendall.com.br.calendallpro.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import calendall.com.br.calendallpro.BuildConfig;
import calendall.com.br.calendallpro.R;
import calendall.com.br.calendallpro.dtoIN.LoginIN;
import calendall.com.br.calendallpro.dtoOUT.LoginOUT;
import calendall.com.br.calendallpro.service.CallService;
import calendall.com.br.calendallpro.service.CallServiceInterface;
import calendall.com.br.calendallpro.service.ServiceName;
import calendall.com.br.calendallpro.util.SharedUtil;
import calendall.com.br.calendallpro.util.Utils;

public class LoginActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    EditText email;
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this.getApplication());

        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        email = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest( loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted( JSONObject object, GraphResponse response) {

                        String nome = object.optString("name");
                        String email = object.optString("email");
                        String id = object.optString("id");

                        if (BuildConfig.DEBUG) {
                            String asd = String.format("%s %s %s", nome, email, id);
                            Log.i("FACE", asd);
                        }

                        login(email, id);
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

    public void onLogin(View v) {
        String email = this.email.getText().toString();
        String senha = this.senha.getText().toString();

        boolean valido = true;
        if (!(Utils.isEmailValid(email))) {
            this.email.setError("Email inválido!");
            valido = false;
        }

        if (senha.isEmpty()) {
            this.senha.setError("Este campo é obrigatório!");
            valido = false;
        }

        if (valido) {
            login(email, senha);
        }
    }

    public void login(final String email, final String senha) {
        final Gson gson = new Gson();
        CallService callService = new CallService(this, new CallServiceInterface() {
            @Override
            public void postCallService(String string) {
                LoginIN in = gson.fromJson(string, LoginIN.class);

                if (in != null && in.isOk()) {
                    SharedUtil sharedUtil = new SharedUtil(LoginActivity.this);
                    sharedUtil.setPreferences(SharedUtil.KEY_ID, String.valueOf(in.getId()));
                    sharedUtil.setPreferences(SharedUtil.KEY_NOME, in.getNome());
                    sharedUtil.setPreferences(SharedUtil.KEY_EMAIL, email);
                    sharedUtil.setPreferences(SharedUtil.KEY_SENHA, senha);

                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Usuário não encontrado!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        LoginOUT out = new LoginOUT(email, senha);
        callService.execute(ServiceName.LOGIN, gson.toJson(out));
    }

    public void onEsqueciSenha(View v) {
        Intent intent = new Intent(this, EsqueciSenhaActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
