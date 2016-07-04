package calendall.com.br.calendallpro.util;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FacebookUtil {

    public static void printHashKey(Activity activity) {
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo("calendall.com.br.calendallpro", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("NameNotFoundEx", e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Log.e("NoSuchAlgorithmEx", e.getMessage());
        }
    }
}
