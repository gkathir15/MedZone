package com.kani.medzone

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.AlarmManagerCompat
import java.util.*

/**Created by Guru kathir.J on 13,May,2021 **/
class AlarmManagerHelper {

    companion object {
        fun setAlarmTablets(context: Context, time: Calendar, duration: String, reqId: Int) {
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                intent.putExtra(Constants.DURATION, duration)
                intent.putExtra(Constants.callFOR, Constants.TABLET_ALARM)
                PendingIntent.getBroadcast(context, reqId, intent, 0)
            }

            alarmMgr.setExactAndAllowWhileIdle (
                AlarmManager.RTC_WAKEUP,
                time.timeInMillis,
                alarmIntent
            )
        }

        fun setAlarmForSync(context: Context) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 1)
            calendar.set(Calendar.MINUTE, 30)
            calendar.add(Calendar.DATE, 1)
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                intent.putExtra(Constants.callFOR, Constants.SYNC)
                PendingIntent.getBroadcast(context,
                    3, intent, 0)
            }
            alarmMgr.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
            )


//            val calendar1 = Calendar.getInstance()
//            calendar1.add(Calendar.MINUTE, 1
//            )
//
//            val secondIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
//                intent.putExtra(Constants.callFOR, Constants.SYNC)
//                PendingIntent.getBroadcast(context, 0, intent, 0)
//            }
//            alarmMgr.setInexactRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar1.timeInMillis,
//                AlarmManager.INTERVAL_DAY,
//                secondIntent
//            )
        }
    }


}
