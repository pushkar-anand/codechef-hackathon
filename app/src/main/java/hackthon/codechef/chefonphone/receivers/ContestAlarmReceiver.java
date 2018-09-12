package hackthon.codechef.chefonphone.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Objects;

import hackthon.codechef.chefonphone.constants.StringKeys;

public class ContestAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), StringKeys.CONTEST_ALARM_BROADCAST_ACTION)) {

        }

    }
}
