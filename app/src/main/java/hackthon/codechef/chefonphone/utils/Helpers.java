package hackthon.codechef.chefonphone.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.activities.ContestListActivity;
import hackthon.codechef.chefonphone.activities.MainActivity;
import hackthon.codechef.chefonphone.activities.PracticeActivity;
import hackthon.codechef.chefonphone.constants.SharedPrefKeys;
import hackthon.codechef.chefonphone.constants.StringKeys;

public class Helpers {

    private static String bytesToHexString(byte[] bytes) {

        StringBuffer sb = new StringBuffer();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static String getSHA256hash(String input) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((input + Internet.getSecret()).getBytes("UTF-8"));

            return bytesToHexString(hash);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return input;
        }

    }

    private static String buildBaseUrl(Context context, String base_url) {
        SharedPreferences preferences =
                context.getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE);

        String loginToken = preferences.getString(SharedPrefKeys.LOGIN_KEY, "");
        String user = preferences.getString(SharedPrefKeys.CODECHEF_HANDLE, "");

        return base_url + "?user=" + user + "&token=" + loginToken;
    }

    public static String buildUrl(Context context, String base_url) {
        String url = buildBaseUrl(context, base_url);
        String hash = getSHA256hash(url);
        url = url + "&hash=" + hash;
        Log.d("BUILD_URL", url);
        return url;
    }

    public static String buildUrl(Context context, String base_url, Integer offset) {
        String url = buildBaseUrl(context, base_url) + "&offset=" + String.valueOf(offset);
        String hash = getSHA256hash(url);
        url = url + "&hash=" + hash;
        Log.d("BUILD_URL", url);
        return url;
    }

    public static String buildContestDetailsUrl(Context context, String base_url, String contest_code) {
        String url = buildBaseUrl(context, base_url) + "&contest_code=" + contest_code;
        String hash = getSHA256hash(url);
        url = url + "&hash=" + hash;
        Log.d("BUILD_URL", url);
        return url;

    }

    private static void startPracticeActivity(Context context, String level) {

        Intent practice_intent = new Intent(context, PracticeActivity.class);
        practice_intent.putExtra(StringKeys.PRACTICE_ACTVITY_INTENT_KEY, level);
        context.startActivity(practice_intent);

    }

    public static void updateNavHeader(Context context, String name, String username) {

    }


    public static void handleDrawerNavigation(Context context, Integer id) {
        switch (id) {
            case R.id.beginner:
                startPracticeActivity(context, StringKeys.PRACTICE_LEVEL_BEGINNER);
                break;
            case R.id.easy:
                startPracticeActivity(context, StringKeys.PRACTICE_LEVEL_EASY);
                break;
            case R.id.medium:
                startPracticeActivity(context, StringKeys.PRACTICE_LEVEL_MEDIUM);
                break;
            case R.id.hard:
                startPracticeActivity(context, StringKeys.PRACTICE_LEVEL_HARD);
                break;
            case R.id.challenge:
                startPracticeActivity(context, StringKeys.PRACTICE_LEVEL_CHALLENGE);
                break;
            case R.id.peer:
                startPracticeActivity(context, StringKeys.PRACTICE_LEVEL_PEER);
                break;
            case R.id.home:
                Intent home = new Intent(context, MainActivity.class);
                context.startActivity(home);
                break;
            case R.id.ide:
                Intent ide = new Intent(context, IDE_Activity.class);
                context.startActivity(ide);
                break;
            case R.id.contest:
                Intent contest = new Intent(context, ContestListActivity.class);
                context.startActivity(contest);
                break;
        }
    }

    public static void updateDrawerNavHeader(Context context, View navHeaderView) {
        SharedPreferences preferences = context.getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE);

        String name = preferences.getString(SharedPrefKeys.FULLNAME, "Full Name");
        TextView fullName = navHeaderView.findViewById(R.id.fullname);
        fullName.setText(name);

        String handle = "CodeChef Handle : " + preferences.getString(SharedPrefKeys.CODECHEF_HANDLE, "CodeChef Handle");
        TextView codechefHandle = navHeaderView.findViewById(R.id.codechef_handle);
        codechefHandle.setText(handle);
    }

    public static void logout(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.deleteSharedPreferences(SharedPrefKeys.LOGIN_PREF);
        } else {
            context.getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE).edit().clear().apply();
        }
    }
}
