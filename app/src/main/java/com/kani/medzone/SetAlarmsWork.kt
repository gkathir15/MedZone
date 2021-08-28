package com.kani.medzone

import android.app.Notification.DEFAULT_ALL
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color.BLUE
import android.graphics.Color.RED
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_NOTIFICATION_RINGTONE
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.media.RingtoneManager.getDefaultUri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import androidx.work.*
import androidx.work.ListenableWorker.Result.success
import java.util.concurrent.TimeUnit
import android.media.RingtoneManager




/**Created by Guru kathir.J on 04,August,2021 **/
class SetAlarmsWork(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val id = inputData.getLong("ID", 0).toInt()
        val callFor = inputData.getString(Constants.callFOR)
        val duration = inputData.getString(Constants.DURATION)
        callFor?.let {
            if (duration != null) {
                sendNotification(id, it,duration)
            }
        }

        return success()
    }

    private fun sendNotification(id: Int,callFor:String,duRATION:String) {


        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(applicationContext, MainActivity::class.java).also {
            it.putExtra(Constants.callFOR,callFor)
            it.putExtra(Constants.DURATION,duRATION)
            it.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
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
        val subtitleNotification = applicationContext.getString(R.string.tablet)
        val pendingIntent = getActivity(applicationContext, 0, notificationIntent, 0)
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setLargeIcon(bitmap).setSmallIcon(R.drawable.ic_baseline_medical_services_24)
            .setContentTitle(titleNotification).setContentText(subtitleNotification)
            .setDefaults(DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true)
            .setSound(getDefaultUri(TYPE_NOTIFICATION))
            .addAction(NotificationCompat.Action(R.drawable.ic_baseline_done_all_24,"Take all",takeAll))
            .addAction(NotificationCompat.Action(R.drawable.ic_baseline_snooze_24,"Snooze",snooze))
            .addAction(NotificationCompat.Action(R.drawable.ic_baseline_skip_next_24,"Skip",skip))
            .setAutoCancel(true)

        notification.priority = PRIORITY_MAX

        if (SDK_INT >= O) {
            notification.setChannelId(NOTIFICATION_CHANNEL)

            val ringtoneManager = getDefaultUri(TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder().setUsage(USAGE_NOTIFICATION_RINGTONE)
                .setContentType(CONTENT_TYPE_SONIFICATION).build()

            val channel =
                NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_NAME, IMPORTANCE_HIGH)

            channel.enableLights(true)
            channel.lightColor = BLUE
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            channel.setSound(ringtoneManager, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notification.build())
    }

    companion object {
        const val NOTIFICATION_NAME = "appName"
        const val NOTIFICATION_CHANNEL = "appName_channel_01"

        fun scheduleNotification(
            delayInDiff: Long,
            notificationId: Int,
            context: Context,
            workName: String,
            callFor: String
        ) {
            val data = Data.Builder()
                .putInt("ID",notificationId)
                .putString(Constants.callFOR,callFor)
                .putString(Constants.DURATION,workName)
                .build()
            val notificationWork = OneTimeWorkRequest.Builder(SetAlarmsWork::class.java)
                .setInitialDelay(delayInDiff, TimeUnit.MILLISECONDS).setInputData(data).build()

            val instanceWorkManager = WorkManager.getInstance(context)
            instanceWorkManager.beginUniqueWork(workName, ExistingWorkPolicy.REPLACE, notificationWork).enqueue()
        }
    }





}