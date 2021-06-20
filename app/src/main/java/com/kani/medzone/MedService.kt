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
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData


class MedService : Service() {

    private var notification: NotificationCompat.Builder? = null
    private var notificationManager: NotificationManager? = null
    val channelId = "TABLETS NOTIFICATION"
    private var remoteView: RemoteViews? = null
    private val datastore: DataStore<Preferences> by preferencesDataStore(name = "MedZone_datastore")

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val callFor = intent?.getStringExtra(Constants.callFOR)
        notification = startCountDownNotification(this,callFor)
        startForeground(1, notification?.build())

        val dStore = datastore.data.asLiveData().value


        if (callFor == Constants.setNewAlarm || callFor == Constants.SYNC||callFor==Constants.resetAlarmPostBoot) {
            setDailyAlarms(dStore)

            AlarmManagerHelper.setAlarmForSync(applicationContext)
            stopThisService()

        }else if (callFor == Constants.TABLET_ALARM)
        {
            // TODO Do for showing tablet

        }

        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


//    private fun updateNotification(isTimerDone: Boolean, isSuccessFulUserId: Boolean, countDown: String, userID: String) {
//        if (!isTimerDone) {
//            remoteView?.setTextViewText(R.id.timerTxt, countDown)
//        } else {
//            val notificationIntent = Intent(this, HomeScreen::class.java).also {
//                it.putExtra("FromPushNotification",false)
//                it.putExtra("PushAction","AccountOnBoarding")
//                it.putExtra("notificationType","AccountOnBoarding")
//                it.putExtra("isSuccessFulClientId",isSuccessFulUserId)
//                it.putExtra("id",userID)
//            }
//
//            //notification?.setOngoing(false)
//            remoteView?.setViewVisibility( R.id.timerTxt, View.GONE)
//
//
//            if (isSuccessFulUserId) {
//                notificationIntent.putExtra("isSuccessFulClientId",true)
//                remoteView?.setTextViewText(R.id.startTxt, "Your account has been created. Use client code $userID to login.")
//                (application as AppState).saveUserName(userID)
//            } else {
//                notificationIntent.putExtra("isSuccessFulClientId",false)
//                remoteView?.setTextViewText(R.id.startTxt, "Your account creation is taking more than usual time. Our executive will get in touch with you shortly to resolve any queries.")
//
//            }
//            val pendingIntent = PendingIntent.getActivity(this,
//                0, notificationIntent, 0)
//            notification?.setContentIntent(pendingIntent)
//            notification?.setAutoCancel(true)
//            val build = notification?.build()
//            build?.flags = Notification.FLAG_AUTO_CANCEL
//            notificationManager?.notify(2, build)
//            return
//        }
//
//
//        notificationManager?.notify(1, notification?.build())
//    }


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
            .addAction(NotificationCompat.Action(R.drawable.ic_baseline_done_all_24,"Take all",pendingIntent))
            .addAction(NotificationCompat.Action(R.drawable.ic_baseline_snooze_24,"Snooze",pendingIntent))
            .addAction(NotificationCompat.Action(R.drawable.ic_baseline_skip_next_24,"Skip",pendingIntent))
            .setOngoing(true)

    }

    private fun setDailyAlarms(dStore: Preferences?) {
        if (CalHelper.compareBreakfastTime(dStore)) {
            AlarmManagerHelper.setAlarmforTablets(
                applicationContext,
                CalHelper.breakfastTime(dStore),
                Constants.BREAKFAST
            )
        }
        if (CalHelper.compareLunch(dStore)) {
            AlarmManagerHelper.setAlarmforTablets(
                applicationContext,
                CalHelper.lunchtTime(dStore),
                Constants.LUNCH
            )
        }
        if (CalHelper.compareDinner(dStore)) {
            AlarmManagerHelper.setAlarmforTablets(
                applicationContext,
                CalHelper.dinnerTime(dStore),
                Constants.DINNER
            )
        }
        if (CalHelper.compareEvening(dStore)) {
            AlarmManagerHelper.setAlarmforTablets(
                applicationContext,
                CalHelper.dinnerTime(dStore),
                Constants.EVENING
            )
        }
    }
}
