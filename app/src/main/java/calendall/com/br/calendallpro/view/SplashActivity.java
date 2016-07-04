package calendall.com.br.calendallpro.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import calendall.com.br.calendallpro.R;
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
        String login = sharedUtil.getPreferences(SharedUtil.KEY_LOGIN);
        String senha = sharedUtil.getPreferences(SharedUtil.KEY_SENHA);

        if (login == null || senha == null) {
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            startActivity(intent);

            finish();
        } else {
            Toast.makeText(this, "nao null", Toast.LENGTH_SHORT).show();
        }

    }
}
