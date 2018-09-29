package hackthon.codechef.chefonphone.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import hackthon.codechef.chefonphone.constants.SharedPrefKeys;

public class UrlBuilder {

    private static String buildBaseUrl(Context context, String base_url) {
        SharedPreferences preferences =
                context.getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE);

        String loginToken = preferences.getString(SharedPrefKeys.LOGIN_KEY, "");
        String user = preferences.getString(SharedPrefKeys.CODECHEF_HANDLE, "");

        return base_url + "?user=" + user + "&token=" + loginToken;
    }

    public static String buildUrl(Context context, String base_url) {
        String url = buildBaseUrl(context, base_url);
        String hash = Helpers.getSHA256hash(url);
        url = url + "&hash=" + hash;
        Log.d("BUILD_URL", url);
        return url;
    }

    public static String buildUrl(Context context, String base_url, Integer offset) {
        String url = buildBaseUrl(context, base_url) + "&offset=" + String.valueOf(offset);
        String hash = Helpers.getSHA256hash(url);
        url = url + "&hash=" + hash;
        Log.d("BUILD_URL", url);
        return url;
    }

    public static String buildStatusUrl(Context context, String base_url, String status_code) {
        String url = buildBaseUrl(context, base_url) + "&status=" + status_code;
        String hash = Helpers.getSHA256hash(url);
        url = url + "&hash=" + hash;
        Log.d("BUILD_URL", url);
        return url;
    }

    public static String buildContestDetailsUrl(Context context, String base_url, String contest_code) {
        String url = buildBaseUrl(context, base_url) + "&contest_code=" + contest_code;
        String hash = Helpers.getSHA256hash(url);
        url = url + "&hash=" + hash;
        Log.d("BUILD_URL", url);
        return url;

    }

    public static String buildProblemDetailsUrl(Context context, String base_url, String contest, String problem) {
        String url = buildBaseUrl(context, base_url) + "&contest_code=" + contest + "&problem_code=" + problem;
        String hash = Helpers.getSHA256hash(url);
        url = url + "&hash=" + hash;
        Log.d("BUILD_URL", url);
        return url;
    }
}
