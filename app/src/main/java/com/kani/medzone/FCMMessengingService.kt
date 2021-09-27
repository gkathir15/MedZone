package com.kani.medzone

import android.app.NotificationChannel
import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager

import android.app.PendingIntent
import android.content.Context
import android.os.Build

import android.util.Log
import androidx.core.app.NotificationCompat
import com.kani.medzone.ui.AdminActivity


/**Created by Guru kathir.J on 29,August,2021 **/
class FCMMessengingService: FirebaseMessagingService() {
    private val TAG = "DealsMessagingService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d(TAG, "Received message " + remoteMessage.from)
        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data " + remoteMessage.data)

        }
        if (remoteMessage.notification != null) {
            Log.d(
                TAG, "Message Notification "
                        + remoteMessage.notification!!.body
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                sendNotification(remoteMessage.notification, remoteMessage.data.getOrDefault(Constants.PHONE_NUMBER," "))
            }else{
                sendNotification(remoteMessage.notification,
                    remoteMessage.data.get(Constants.PHONE_NUMBER).toString()
                )
            }
        }
           super.onMessageReceived(remoteMessage)

    }



    private fun sendNotification(notification: RemoteMessage.Notification?, phNo: String) {
        val intent = Intent(this, AdminActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(Constants.PHONE_NUMBER,phNo)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, "fcm-channel")
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle(notification!!.title)
            .setContentText(notification.body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =

                NotificationChannel(SetAlarmsWork.NOTIFICATION_CHANNEL, SetAlarmsWork.NOTIFICATION_NAME, NotificationManager.IMPORTANCE_HIGH)

        notificationManager.createNotificationChannel(channel)
        notificationBuilder.setChannelId(channel.id)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }



    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
    override fun handleIntent(intent: Intent) {
        super.handleIntent(intent)
    }
}