package com.kani.medzone

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat

/**Created by Guru kathir.J on 10,September,2021 **/
class NotificationObj {
    companion object{
         fun sendNotification(id: Int,callFor:String,duRATION:String,applicationContext:Context) {


            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationIntent = Intent(applicationContext, MainActivity::class.java).also {
                it.putExtra(Constants.callFOR,callFor)
                it.putExtra(Constants.DURATION,duRATION)
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                it.putExtra("ID", id)
            }


            val takeAll = PendingIntent.getActivity(
                applicationContext,
                2, notificationIntent.putExtra(Constants.NotificationAction,Constants.takeAll), 0
            )
            val snooze = PendingIntent.getActivity(
                applicationContext,
                3, notificationIntent.putExtra(Constants.NotificationAction,Constants.SNOOZE), 0
            )
            val skip = PendingIntent.getActivity(
                applicationContext,
                4, notificationIntent.putExtra(Constants.NotificationAction,Constants.SKIP), 0)


            val bitmap = applicationContext.vectorToBitmap(R.drawable.ic_baseline_medical_services_24)
            val titleNotification = applicationContext.getString(R.string.app_name)
            val subtitleNotification = duRATION
            val pendingIntent =
                PendingIntent.getActivity(applicationContext, 0, notificationIntent, 0)
            val notification = NotificationCompat.Builder(applicationContext,
                SetAlarmsWork.NOTIFICATION_CHANNEL
            )
                .setLargeIcon(bitmap).setSmallIcon(R.drawable.ic_baseline_medical_services_24)
                .setContentTitle(titleNotification).setContentText(subtitleNotification)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .addAction(NotificationCompat.Action(R.drawable.ic_baseline_done_all_24,"Take all",takeAll))
                .addAction(NotificationCompat.Action(R.drawable.ic_baseline_snooze_24,"Snooze",snooze))
                .addAction(NotificationCompat.Action(R.drawable.ic_baseline_skip_next_24,"Skip",skip))
                .setDeleteIntent(pendingIntent)

            notification.priority = NotificationCompat.PRIORITY_MAX

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notification.setChannelId(SetAlarmsWork.NOTIFICATION_CHANNEL)

                val ringtoneManager =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()

                val channel =
                    NotificationChannel(SetAlarmsWork.NOTIFICATION_CHANNEL, SetAlarmsWork.NOTIFICATION_NAME, NotificationManager.IMPORTANCE_HIGH)

                channel.enableLights(true)
                channel.lightColor = Color.BLUE
                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                channel.setSound(ringtoneManager, audioAttributes)
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(id, notification.build())
        }
    }
}