package com.kani.medzone

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MedService : Service() {

    private var notification: NotificationCompat.Builder? = null
    private var notificationManager: NotificationManager? = null
    val channelId = "TABLETS NOTIFICATION"
    private var remoteView: RemoteViews? = null
    private var datastore: DataStore<Preferences>?=null
    private var dStore: Preferences?=null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        datastore = (application as AppState).datastore
        val callFor = intent?.getStringExtra(Constants.callFOR)
        notification = startCountDownNotification(this,callFor)
        startForeground(1, notification?.build())

        GlobalScope.launch(Dispatchers.IO) {
             dStore = datastore?.data?.first()?.toPreferences()
        }


        if (callFor == Constants.setNewAlarm || callFor == Constants.SYNC||callFor==Constants.resetAlarmPostBoot) {
            setDailyAlarms(dStore)

            AlarmManagerHelper.setAlarmForSync(applicationContext)
            //stopThisService()

        }else if (callFor == Constants.TABLET_ALARM)
        {
            // Do for showing tablet

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
                "Account Opening Journey",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(serviceChannel)

        }

        val notificationIntent = Intent(context, MainActivity::class.java).also {
            it.putExtra("FromPushNotification", true)
            it.putExtra("PushAction", "AccountOnBoarding")
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0, notificationIntent, 0
        )
        val takeAll = PendingIntent.getActivity(
            context,
            0, notificationIntent, 0
        )
        val snooze = PendingIntent.getActivity(
            context,
            0, notificationIntent, 0
        )
        val skip = PendingIntent.getActivity(
            context,
            0, notificationIntent, 0
        )
        remoteView = RemoteViews(context.packageName, R.layout.notification)
        return NotificationCompat.Builder(context, channelId)
            .setContentIntent(pendingIntent)
            .setCustomContentView(remoteView)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setColor(resources.getColor(R.color.white))
            .setOnlyAlertOnce(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setChannelId(channelId)
            .addAction(NotificationCompat.Action(R.drawable.ic_baseline_done_all_24,"Take all",takeAll))
            .addAction(NotificationCompat.Action(R.drawable.ic_baseline_snooze_24,"Snooze",snooze))
            .addAction(NotificationCompat.Action(R.drawable.ic_baseline_skip_next_24,"Skip",skip))
            .setOngoing(true)

    }

    private fun setDailyAlarms(dStore: Preferences?) {
        if (CalHelper.compareBreakfastTime(dStore)) {
            AlarmManagerHelper.setAlarmTablets(
                applicationContext,
                CalHelper.breakfastTime(dStore),
                Constants.BREAKFAST
            )
        }
        if (CalHelper.compareLunch(dStore)) {
            AlarmManagerHelper.setAlarmTablets(
                applicationContext,
                CalHelper.lunchtTime(dStore),
                Constants.LUNCH
            )
        }
        if (CalHelper.compareDinner(dStore)) {
            AlarmManagerHelper.setAlarmTablets(
                applicationContext,
                CalHelper.dinnerTime(dStore),
                Constants.DINNER
            )
        }
        if (CalHelper.compareEvening(dStore)) {
            AlarmManagerHelper.setAlarmTablets(
                applicationContext,
                CalHelper.dinnerTime(dStore),
                Constants.EVENING
            )
        }
    }
}
