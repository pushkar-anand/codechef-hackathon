package hackthon.codechef.chefonphone.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.activities.ContestListActivity;
import hackthon.codechef.chefonphone.activities.IDEActivity;
import hackthon.codechef.chefonphone.activities.MainActivity;
import hackthon.codechef.chefonphone.activities.PracticeActivity;
import hackthon.codechef.chefonphone.constants.SharedPrefKeys;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.constants.Urls;

public class Helpers {

    private static String bytesToHexString(byte[] bytes) {

        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String getSHA256hash(String input) {
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


    private static void startPracticeActivity(Context context, String level) {

        Intent practice_intent = new Intent(context, PracticeActivity.class);
        practice_intent.putExtra(StringKeys.PRACTICE_ACTVITY_INTENT_KEY, level);
        context.startActivity(practice_intent);

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
                Intent ide = new Intent(context, IDEActivity.class);
                context.startActivity(ide);
                break;
            case R.id.contest:
                Intent contest = new Intent(context, ContestListActivity.class);
                context.startActivity(contest);
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static void handleMenuCLicks(Context context, Integer id) {
        SharedPreferences preferences =
                context.getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE);

        String user = preferences.getString(SharedPrefKeys.CODECHEF_HANDLE, "CodeChef Handle");

        if (id == R.id.action_settings) {

            String url = Urls.CODECHEF_USERS_PAGE + user;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
        } else if (id == R.id.action_logout) {
            Helpers.logout(context);
        }

    }

    public static void updateDrawerNavHeader(Context context, View navHeaderView) {
        SharedPreferences preferences =
                context.getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE);

        String name = preferences.getString(SharedPrefKeys.FULLNAME, "Full Name");
        TextView fullName = navHeaderView.findViewById(R.id.fullname);
        fullName.setText(name);

        String handle = "CodeChef Handle : " + preferences.getString(SharedPrefKeys.CODECHEF_HANDLE, "CodeChef Handle");
        TextView codechefHandle = navHeaderView.findViewById(R.id.codechef_handle);
        codechefHandle.setText(handle);
    }

    private static void logout(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.deleteSharedPreferences(SharedPrefKeys.LOGIN_PREF);
        } else {
            context.getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE).edit().clear().apply();
        }

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static String languageToExtension(String language) {


        switch (language) {
            case "C":
                return ".c";
            case "C++14":
                return ".cpp";
            case "C#":
                return ".cs";
            case "CLOJ":
                return ".clj";
            case "HASK":
                return ".hs";
            case "JAVE":
                return ".java";
            case "JS":
                return ".js";
            case "NODEJS":
                return ".js";
            case "PERL":
                return ".pl";
            case "PERL6":
                return ".pm6";
            case "PHP":
                return ".php";
            case "PYTH":
                return ".py";
            case "PYTH 3.6":
                return ".py3";
            case "PYPY":
                return ".py";
            case "RUBY":
                return ".rb";
            case "RUST":
                return ".rs";
            case "SCALA":
                return ".scala";
            default:
                return ".txt";
        }
    }
}
