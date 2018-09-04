package hackthon.codechef.chefonphone.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import hackthon.codechef.chefonphone.constants.SharedPrefKeys;

public class Helpers {

    private static String bytesToHexString(byte[] bytes) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String buildUrl(Context context, String base_url)
    {
        SharedPreferences preferences =
                context.getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE);

        String loginToken = preferences.getString(SharedPrefKeys.LOGIN_KEY, "");
        String user = preferences.getString(SharedPrefKeys.CODECHEF_HANDLE, "");

        String url =  base_url + "?user="+user+"&token="+loginToken;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((url + Internet.getSecret()).getBytes("UTF-8"));


            String strhash = bytesToHexString(hash);

            url =  url + "&hash=" + strhash;
            Log.d("BUILD_URL", url);
            return url;

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return url;
        }
    }
}
