package com.kani.medzone

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MedService : Service() {

    private var notificationManager: NotificationManager? = null
    val channelId = "TABLETS NOTIFICATION"
    private var remoteView: RemoteViews? = null
    private var datastore: DataStore<Preferences>?=null
    private var dStore: Preferences?=null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        datastore = (application as AppState).datastore
        GlobalScope.launch(Dispatchers.IO) {
            dStore = datastore?.data?.first()?.toPreferences()
        }
        val callFor = intent?.getStringExtra(Constants.callFOR)
        startForeground(1, startCountDownNotification(this,callFor).build())


        Log.e("Medservice",callFor)

        if (callFor == Constants.setNewAlarm ||
            callFor==Constants.resetAlarmPostBoot||callFor==Constants.SYNC) {
            setDailyAlarms(dStore)

                 AlarmManagerHelper.setAlarmForSync(applicationContext)
            stopThisService()

        }
        if (callFor == Constants.TABLET_ALARM)
        {

            // Do for showing tablet
            this.getSystemService(NotificationManager::class.java).also {
                it.notify(System.currentTimeMillis().toInt(),startCountDownNotification(this,callFor).build())
            }

            stopThisService()

        }

        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }





    private fun stopThisService() {
        stopForeground(true)

        stopSelf(1)
    }

    private fun startCountDownNotification(context: Context, callFor: String?): NotificationCompat.Builder {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelId,
                "Tablet",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(serviceChannel)

        }

        val notificationIntent = Intent(context, MainActivity::class.java).also {
            it.putExtra(Constants.callFOR,callFor)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            1, notificationIntent, 0
        )
        val takeAll = PendingIntent.getActivity(
            context,
            2, notificationIntent.putExtra(Constants.NotificationAction,Constants.takeAll), 0
        )
        val snooze = PendingIntent.getActivity(
            context,
            3, notificationIntent.putExtra(Constants.NotificationAction,Constants.SNOOZE), 0
        )
        val skip = PendingIntent.getActivity(
            context,
            4, notificationIntent.putExtra(Constants.NotificationAction,Constants.SKIP), 0
        )
        remoteView = RemoteViews(context.packageName, R.layout.notification)
        return NotificationCompat.Builder(context, channelId)
            .setContentIntent(pendingIntent)
            .setContentTitle(getString(R.string.takeTalets))
            .setContentText(getString(R.string.tapto))
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setSmallIcon(R.drawable.ic_baseline_medical_services_24)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setChannelId(channelId)
            .addAction(NotificationCompat.Action(R.drawable.ic_baseline_done_all_24,"Take all",takeAll))
            .addAction(NotificationCompat.Action(R.drawable.ic_baseline_snooze_24,"Snooze",snooze))
            .addAction(NotificationCompat.Action(R.drawable.ic_baseline_skip_next_24,"Skip",skip))
            .setAutoCancel(true)
    }

    private fun setDailyAlarms(dStore: Preferences?) {

            AlarmManagerHelper.setAlarmTablets(
                applicationContext,
                CalHelper.breakfastTime(dStore),
                Constants.BREAKFAST,7
            )


            AlarmManagerHelper.setAlarmTablets(
                applicationContext,
                CalHelper.lunchTime(dStore),
                Constants.LUNCH,6
            )


            AlarmManagerHelper.setAlarmTablets(
                applicationContext,
                CalHelper.eveningTime(dStore),
                Constants.DINNER,5
            )


            AlarmManagerHelper.setAlarmTablets(
                applicationContext,
                CalHelper.dinnerTime(dStore),
                Constants.EVENING,4
            )

    }
}
