package com.egor.balusha.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.egor.balusha.R
import com.egor.balusha.activities.main.view.MainActivity
import com.egor.balusha.activities.main.repository.NotifRepository


object NotifCenter {

    fun sendNotifReminderNotification(context: Context, notifId: Int) {
        val note = NotifRepository.getNotifModelForId(notifId) ?: return

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = setupNotificationChannel(notificationManager)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent =
            PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_ONE_SHOT)

        val notification = NotificationCompat.Builder(context, notificationChannel)
            .setSmallIcon(R.drawable.ic_baseline_watch_later_24)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.ic_baseline_access_alarm_24
                )
            )
            .setContentTitle(note.title)
            .setContentText(note.body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(note.body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
//            .addAction(getSnoozedNotificationIcon())

        notificationManager.notify(notifId, notification.build())
    }

//    private fun getSnoozedNotificationIcon(context: Context): NotificationCompat.Action {
////        val snoozePendingIntent=PendingIntent.getBroadcast(context,)
//        val action= NotificationCompat.Action(android.R.drawable.ic_menu_close_clear_cancel,"Snoozed")
//    }


    private fun setupNotificationChannel(
        notificationManager: NotificationManager
    ): String {
        val notificationChannelId = "1"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                notificationChannelId,
                "Reminder Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        return notificationChannelId
    }
}