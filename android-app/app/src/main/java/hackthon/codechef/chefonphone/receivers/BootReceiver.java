package hackthon.codechef.chefonphone.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Objects;

import hackthon.codechef.chefonphone.utils.AlarmHelpers;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
            AlarmHelpers.restartAllAlarms(context);
        }
    }
}
