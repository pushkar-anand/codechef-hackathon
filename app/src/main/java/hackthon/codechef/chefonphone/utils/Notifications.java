package hackthon.codechef.chefonphone.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.activities.ContestActivity;
import hackthon.codechef.chefonphone.constants.StringKeys;

public class Notifications {

    private static final String CONTEST_NOTIFICATION_CHANNEL_ID = "contest_remind_channel";

    private static final Integer CONTEST_REMINDER_NOTIFICATION_PI_ID = 101;
    private static final Integer CONTEST_REMINDER_NOTIFICATION_ID = 102;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static NotificationChannel getContestReminderChannel() {

        CharSequence channel_name = "Reminder Channel";
        String channel_description =
                "This channel is used to send notifications to remind about upcoming contests only when user asks to be reminded.";

        Integer channel_importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel notificationChannel =
                new NotificationChannel(CONTEST_NOTIFICATION_CHANNEL_ID,
                        channel_name,
                        channel_importance);

        notificationChannel.setDescription(channel_description);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);

        return notificationChannel;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void createContestReminderChannel(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if ((notificationManager != null)) {
            notificationManager.createNotificationChannel(getContestReminderChannel());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createAllNotificationChannels(Context context) {
        context = context.getApplicationContext();
        createContestReminderChannel(context);
    }

    public static void sendContestNotification(Context context, String contest) {
        context = context.getApplicationContext();

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, ContestActivity.class);
        intent.putExtra(StringKeys.CONTEST_ACTVITY_INTENT_KEY, contest);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context,
                        CONTEST_REMINDER_NOTIFICATION_PI_ID,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createAllNotificationChannels(context);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CONTEST_NOTIFICATION_CHANNEL_ID);

        String notificationTitle = "";
        String notificationText = "";

        builder.setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_contest_remind)
                .setContentIntent(pendingIntent)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notificationText)
                        .setBigContentTitle(notificationTitle))
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX);

        if (notificationManager != null) {
            notificationManager.notify(CONTEST_REMINDER_NOTIFICATION_ID, builder.build());
        }
    }
}
