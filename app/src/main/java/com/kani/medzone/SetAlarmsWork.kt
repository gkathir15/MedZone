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
                NotificationObj.sendNotification(id, it,duration,applicationContext)
            }
        }

        return success()
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