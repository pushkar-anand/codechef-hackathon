package hackthon.codechef.chefonphone.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.widget.Toast;

import hackthon.codechef.chefonphone.receivers.BootReceiver;

public class AlarmHelpers {

    private static SharedPreferences alarmPref;

    private static void enableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private static void disableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

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
