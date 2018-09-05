package hackthon.codechef.chefonphone.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.activities.ContestActivity;
import hackthon.codechef.chefonphone.activities.IDE_Activity;
import hackthon.codechef.chefonphone.activities.PracticeActivity;
import hackthon.codechef.chefonphone.constants.SharedPrefKeys;
import hackthon.codechef.chefonphone.constants.StringKeys;

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

    public static String buildUrl(Context context, String base_url, Integer offset) {
        SharedPreferences preferences =
                context.getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE);

        String loginToken = preferences.getString(SharedPrefKeys.LOGIN_KEY, "");
        String user = preferences.getString(SharedPrefKeys.CODECHEF_HANDLE, "");

        String url = base_url + "?user=" + user + "&token=" + loginToken + "&offset=" + String.valueOf(offset);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((url + Internet.getSecret()).getBytes("UTF-8"));


            String strhash = bytesToHexString(hash);

            url = url + "&hash=" + strhash;
            Log.d("BUILD_URL", url);
            return url;

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return url;
        }
    }

    private static void startPracticeActivity(Context context, String level) {

        Intent practice_intent = new Intent(context, PracticeActivity.class);
        practice_intent.putExtra(StringKeys.PRACTICE_ACTVITY_INTENT_KEY, level);
        context.startActivity(practice_intent);

    }


    public static void handleDrawerNavigation(Context context, Integer id) {
        if (id == R.id.beginner) {
            startPracticeActivity(context, StringKeys.PRACTICE_LEVEL_BEGINNER);
        } else if (id == R.id.easy) {
            startPracticeActivity(context, StringKeys.PRACTICE_LEVEL_EASY);
        } else if (id == R.id.medium) {
            startPracticeActivity(context, StringKeys.PRACTICE_LEVEL_MEDIUM);
        } else if (id == R.id.hard) {
            startPracticeActivity(context, StringKeys.PRACTICE_LEVEL_HARD);
        } else if (id == R.id.challenge) {
            startPracticeActivity(context, StringKeys.PRACTICE_LEVEL_CHALLENGE);
        } else if (id == R.id.peer) {
            startPracticeActivity(context, StringKeys.PRACTICE_LEVEL_PEER);
        } else if (id == R.id.ide) {
            Intent ide = new Intent(context, IDE_Activity.class);
            context.startActivity(ide);
        } else if (id == R.id.contest) {
            Intent contest = new Intent(context, ContestActivity.class);
            context.startActivity(contest);
        }
    }

    public static void logout(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.deleteSharedPreferences(SharedPrefKeys.LOGIN_PREF);
        } else {
            context.getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE).edit().clear().apply();
        }
    }
}
