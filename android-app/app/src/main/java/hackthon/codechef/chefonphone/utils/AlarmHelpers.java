package hackthon.codechef.chefonphone.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.databases.AppDatabase;
import hackthon.codechef.chefonphone.receivers.BootReceiver;
import hackthon.codechef.chefonphone.receivers.ContestAlarmReceiver;

public class AlarmHelpers {

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

    private static PendingIntent getContestAlarmPendingIntent(Context context, String contest_code, Integer id) {
        Intent intent = new Intent(context, ContestAlarmReceiver.class);
        intent.putExtra(StringKeys.CONTEST_ACTVITY_INTENT_KEY, contest_code);
        intent.setAction(StringKeys.CONTEST_ALARM_BROADCAST_ACTION);

        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    @SuppressLint("SimpleDateFormat")
    private static long getTimeInMillis(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date mDate = sdf.parse(dateStr);
        return mDate.getTime();
    }

    public static boolean alarmIsSet(Context context, String contest_code) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return appDatabase.isAlarmSet(contest_code);
    }


    public static void setAlarm(Context context, String contest_code, String contest_start) {
        context = context.getApplicationContext();
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        enableBootReceiver(context);

        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Integer ID;

        Long longID = appDatabase.newAlarm(contest_code, contest_start);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ID = Math.toIntExact(longID);
        } else {
            ID = longID.intValue();
        }

        PendingIntent pendingIntent = getContestAlarmPendingIntent(context, contest_code, ID);


        long timeInMilliseconds;
        try {

            timeInMilliseconds = getTimeInMillis(contest_start);
            Log.d("Date in milli ", String.valueOf(timeInMilliseconds));

            if (alarmManager != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                            timeInMilliseconds,
                            pendingIntent);
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                            timeInMilliseconds,
                            pendingIntent);
                }
                Toast.makeText(context, "You will be notified before contest starts.", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("setAlarm", "alarmManager is null");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static void deleteAlarm(Context context, String contest_code) {
        context = context.getApplicationContext();

        AppDatabase appDatabase = AppDatabase.getInstance(context);
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Integer id = appDatabase.getAlarmID(contest_code);
        appDatabase.removeAlarm(contest_code);

        PendingIntent pendingIntent = getContestAlarmPendingIntent(context, contest_code, id);
        if (id != -1) {
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
            }
        }

        if (appDatabase.getAlarmsIDs().size() == 0) {
            disableBootReceiver(context);
        }
        Toast.makeText(context, "Now you will not be notified when contest starts.", Toast.LENGTH_SHORT).show();
    }

    public static void restartAllAlarms(Context context) {
        context = context.getApplicationContext();
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        ArrayList<Integer> alarmIDs = appDatabase.getAlarmsIDs();
        for (Integer id : alarmIDs) {
            Cursor cursor = appDatabase.getAlarmData(id);

            String contest = cursor.getString(cursor.getColumnIndex(AppDatabase.ALARMS_TABLE_COLUMN_CONTEST));
            String date = cursor.getString(cursor.getColumnIndex(AppDatabase.ALARMS_TABLE_COLUMN_TIME));

            PendingIntent pendingIntent = getContestAlarmPendingIntent(context, contest, id);

            try {
                long millis = getTimeInMillis(date);
                if (alarmManager != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                                millis,
                                pendingIntent);
                    } else {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                                millis,
                                pendingIntent);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
}
