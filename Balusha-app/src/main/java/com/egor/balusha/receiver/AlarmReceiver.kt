package com.egor.balusha.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        CoroutineScope(Dispatchers.Default).launch {
            if (intent != null && intent.action.equals("notification1")) {
                val notifId =
                    intent.getIntExtra("notifId", 0)

                NotifCenter.sendNotifReminderNotification(context, notifId)
            }
        }
    }
}