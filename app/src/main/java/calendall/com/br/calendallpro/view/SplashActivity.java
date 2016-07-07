package calendall.com.br.calendallpro.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.Gson;

import calendall.com.br.calendallpro.R;
import calendall.com.br.calendallpro.dtoIN.LoginIN;
import calendall.com.br.calendallpro.dtoOUT.LoginOUT;
import calendall.com.br.calendallpro.service.CallService;
import calendall.com.br.calendallpro.service.CallServiceInterface;
import calendall.com.br.calendallpro.service.ServiceName;
import calendall.com.br.calendallpro.util.SharedUtil;

public class SplashActivity extends Activity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(this, 1000);
    }

    @Override
    public void run() {
        SharedUtil sharedUtil = new SharedUtil(this);
        String id = sharedUtil.getPreferences(SharedUtil.KEY_ID);

        Intent intent;
        if (id == null) {
            intent = new Intent(this, LoginRegisterActivity.class);
            startActivity(intent);
            finish();
        } else {
            String email = sharedUtil.getPreferences(SharedUtil.KEY_EMAIL);
            String senha = sharedUtil.getPreferences(SharedUtil.KEY_SENHA);
            login(email, senha);
        }
    }

    public void login(final String email, final String senha) {
        final Gson gson = new Gson();
        CallService callService = new CallService(this, new CallServiceInterface() {
            @Override
            public void postCallService(String string) {
                LoginIN in = gson.fromJson(string, LoginIN.class);

                if (in.isOk()) {
                    SharedUtil sharedUtil = new SharedUtil(SplashActivity.this);
                    sharedUtil.setPreferences(SharedUtil.KEY_ID, String.valueOf(in.getId()));
                    sharedUtil.setPreferences(SharedUtil.KEY_NOME, in.getNome());
                    sharedUtil.setPreferences(SharedUtil.KEY_EMAIL, email);
                    sharedUtil.setPreferences(SharedUtil.KEY_SENHA, senha);

                    Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginRegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        LoginOUT out = new LoginOUT(email, senha);
        callService.execute(ServiceName.LOGIN, gson.toJson(out));
    }
}
