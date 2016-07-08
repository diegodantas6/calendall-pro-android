package calendall.com.br.calendallpro.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import calendall.com.br.calendallpro.BuildConfig;
import calendall.com.br.calendallpro.util.SharedUtil;
import calendall.com.br.calendallpro.util.Utils;

public class CallService extends AsyncTask<String, Void, String>{

    private ProgressDialog progressDialog;

    private Activity activity;
    private CallServiceInterface callServiceInterface;

    public CallService(Activity activity, CallServiceInterface callServiceInterface) {
        this.activity = activity;
        this.callServiceInterface = callServiceInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (Utils.isConnected(activity)) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Processando!");
            progressDialog.show();
        } else {
            Toast.makeText(activity, "Favor conectar a internet!", Toast.LENGTH_LONG).show();
            super.cancel(true);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL("http://192.168.0.13:8080/calendall-service/rest/".concat(strings[0]));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");

            connection.setConnectTimeout(5000);

            SharedUtil sharedUtil = new SharedUtil(this.activity);
            if (sharedUtil.getPreferences(SharedUtil.KEY_ID) != null) {
                connection.setRequestProperty("user", sharedUtil.getPreferences(SharedUtil.KEY_EMAIL));
                connection.setRequestProperty("pass", sharedUtil.getPreferences(SharedUtil.KEY_SENHA));
            }

            if (strings[1] != null) {
                byte[] outputInBytes = strings[1].getBytes("UTF-8");
                OutputStream os = connection.getOutputStream();
                os.write( outputInBytes );
                os.close();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer();

            String tmp = "";

            while((tmp=reader.readLine()) != null)
                json.append(tmp);

            reader.close();

            return json.toString();
        }catch(Exception e) {
            Log.e("ERRO:", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String retorno) {
        super.onPostExecute(retorno);
        callServiceInterface.postCallService(retorno);
        progressDialog.dismiss();
    }
}
