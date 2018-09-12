package hackthon.codechef.chefonphone.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class Notifications {

    private static final String CONTEST_NOTIFICATION_CHANNEL_ID = "contest_remind_channel";

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
    public void createAllNotificationChannels(Context context) {
        context = context.getApplicationContext();
        createContestReminderChannel(context);
    }
}
