package calendall.com.br.calendallpro.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import calendall.com.br.calendallpro.R;

public class LoginRegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
    }

    public void onLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onRegister(View view) {
        Intent intent = new Intent(this, RegisterLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
