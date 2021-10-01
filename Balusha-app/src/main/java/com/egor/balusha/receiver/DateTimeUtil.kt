package com.egor.balusha.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.egor.balusha.R
import com.egor.balusha.util.BalushaApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

object DateTimeUtil {

    suspend fun setAlarmForReminder(timeMillis: Long, notifId: Int) {
        withContext(Dispatchers.Main) {
            val cal = Calendar.getInstance()
                .setTimeMillis(timeMillis)
                .setFiled(Calendar.SECOND, 0)
                .setFiled(Calendar.MILLISECOND, 0)

            val context = BalushaApplication.getApplicationInstance()
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, AlarmReceiver::class.java)
            intent.action = "notification1"
            intent.putExtra("notifId", notifId)
            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    notifId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
        }
    }
}

fun Calendar.addField(field: Int, amount: Int): Calendar {
    this.add(field, amount)
    return this
}

fun Calendar.setFiled(field: Int, amount: Int): Calendar {
    this.set(field, amount)
    return this
}

fun Calendar.setTimeMillis(amount: Long): Calendar {
    this.timeInMillis = amount
    return this
}