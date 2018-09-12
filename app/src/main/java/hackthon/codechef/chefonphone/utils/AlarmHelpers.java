package hackthon.codechef.chefonphone.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class AlarmHelpers {

    private static SharedPreferences alarmPref;

    public static boolean alarmIsSet(Context context, String contest_code) {
        return false;
    }

    public static void setAlarm(Context context, String contest_code, String contest_start) {

        Toast.makeText(context, "You will be notified before contest starts.", Toast.LENGTH_SHORT).show();
    }

    public static void deleteAlarm(Context context, String contest_code) {

        Toast.makeText(context, "Now you will not be notified when contest starts.", Toast.LENGTH_SHORT).show();
    }
}
