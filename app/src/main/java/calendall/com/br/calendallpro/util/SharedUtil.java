package calendall.com.br.calendallpro.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtil {

    public static final String KEY_ID = "id";
    public static final String KEY_NOME = "nome";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_SENHA = "senha";

    private SharedPreferences sharedPref;

    public SharedUtil(Activity activity) {
        this.sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public void setPreferences(String key, String valor) {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString(key, valor);
        editor.commit();
    }

    public String getPreferences(String key) {
        String valor = this.sharedPref.getString(key, null);
        return valor;
    }

    public void clearPreferences() {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.clear();
        editor.commit();
    }

}
