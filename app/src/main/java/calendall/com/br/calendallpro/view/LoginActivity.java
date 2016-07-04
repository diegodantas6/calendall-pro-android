package calendall.com.br.calendallpro.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;

import java.util.Arrays;

import calendall.com.br.calendallpro.R;
import calendall.com.br.calendallpro.util.SharedUtil;

public class LoginActivity extends AppCompatActivity {

    ProfilePictureView loginFoto;
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this.getApplication());

        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginFoto = (ProfilePictureView) findViewById(R.id.login_foto);

        //loginButton.setReadPermissions(Arrays.asList("public_profile"));
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                loginResult.getAccessToken().getUserId();

                /**
                final AccessToken accessToken = loginResult.getAccessToken();
                GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                        String name = user.optString("name");
                        String id = user.optString("id");

                        SharedUtil sharedUtil = new SharedUtil(LoginActivity.this);

                        sharedUtil.setPreferences(SharedUtil.KEY_NOME, name);
                        sharedUtil.setPreferences(SharedUtil.KEY_LOGIN, id);
                        sharedUtil.setPreferences(SharedUtil.KEY_SENHA, id);

                        Log.d("Login", "salvando preferencias");
                        Log.d("Nome", sharedUtil.getPreferences(SharedUtil.KEY_NOME));
                        Log.d("ID", sharedUtil.getPreferences(SharedUtil.KEY_LOGIN));
                    }
                }).executeAsync();
                 */

                GraphRequest request = GraphRequest.newMeRequest( loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted( JSONObject object, GraphResponse response) {

                        String nome = object.optString("name");
                        String email = object.optString("email");
                        String id = object.optString("id");

                        Log.d("Nome", nome);
                        Log.d("email", email);
                        Log.d("ID", id);

                        loginFoto.setProfileId(id);

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
}
