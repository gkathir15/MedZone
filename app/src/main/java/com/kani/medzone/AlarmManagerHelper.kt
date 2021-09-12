package com.kani.medzone

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.util.*

/**Created by Guru kathir.J on 13,May,2021 **/
class AlarmManagerHelper {

    companion object {
//        fun setAlarmTablets(context: Context, time: Calendar, duration: String, reqId: Int) {
//            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
//                intent.putExtra(Constants.DURATION, duration)
//                intent.putExtra(Constants.callFOR, Constants.TABLET_ALARM)
//                PendingIntent.getBroadcast(context, reqId, intent, 0)
//            }
//
//            alarmMgr.setExactAndAllowWhileIdle (
//                AlarmManager.RTC_WAKEUP,
//                time.timeInMillis,
//                alarmIntent
//            )
//        }

        fun setAlarmForSync(context: Context) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 7)
            calendar.set(Calendar.MINUTE, 30)
            calendar.add(Calendar.DATE, 1)
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                intent.putExtra(Constants.callFOR, Constants.SYNC)
                PendingIntent.getBroadcast(context,
                    3, intent, FLAG_ONE_SHOT)
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

        fun setDailyAlarms(dStore: Preferences, applicationContext:Context) {

            setAlarmTablets(
                applicationContext,
                CalHelper.breakfastTime(dStore).timeInMillis ,
                Constants.BREAKFAST,7,Constants.TABLET_ALARM
            )


            setAlarmTablets(
                applicationContext,
                CalHelper.lunchTime(dStore).timeInMillis ,
                Constants.LUNCH,6,Constants.TABLET_ALARM
            )


            setAlarmTablets(
                applicationContext,
                CalHelper.eveningTime(dStore).timeInMillis ,
                Constants.DINNER,5,Constants.TABLET_ALARM
            )


            setAlarmTablets(
                applicationContext,
                CalHelper.dinnerTime(dStore).timeInMillis ,
                Constants.EVENING,4,Constants.TABLET_ALARM
            )

        }

         fun setAlarmTablets(context:Context, timeDiff:Long, dayMealConstant:String, notifyId:Int,callFor:String)
        {
           // SetAlarmsWork.scheduleNotification(timeDiff,notifyId,context,dayMealConstant,callFor)
                        val intent = Intent(context, AlarmReceiver::class.java).let { intent ->
                intent.putExtra(Constants.callFOR, Constants.TABLET_ALARM)
                            intent.putExtra("ID",notifyId)
                                intent.putExtra(Constants.callFOR,callFor)
                                intent.putExtra(Constants.DURATION,dayMealConstant)
                PendingIntent.getBroadcast(context, notifyId, intent, FLAG_ONE_SHOT)
            }

            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,timeDiff,intent)

        }
    }


}
