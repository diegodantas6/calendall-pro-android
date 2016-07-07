package calendall.com.br.calendallpro.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;

import org.w3c.dom.Text;

import calendall.com.br.calendallpro.R;
import calendall.com.br.calendallpro.util.SharedUtil;

public class MenuActivity extends AppCompatActivity {

    TextView nome;
    SharedUtil sharedUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_menu);

        nome = (TextView) findViewById(R.id.nome);

        sharedUtil = new SharedUtil(this);
        nome.setText(sharedUtil.getPreferences(SharedUtil.KEY_NOME));
    }

    public void onLimpar(View v) {
        LoginManager.getInstance().logOut();
        sharedUtil.clearPreferences();
        finish();
    }
}
