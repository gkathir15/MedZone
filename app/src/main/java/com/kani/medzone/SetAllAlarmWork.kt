package com.kani.medzone

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.work.*
import androidx.work.ListenableWorker.Result.success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**Created by Guru kathir.J on 04,August,2021 **/
class SetAllAlarmWork(val context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {

        GlobalScope.launch(Dispatchers.IO) {
          val   dStore =  (context.applicationContext as AppState).datastore?.data?.first()?.toPreferences()
            launch (Dispatchers.Main){
                setDailyAlarms(dStore)
            }
        }
        return success()
    }



    companion object {
        const val NOTIFICATION_ID = "appName_notification_id"
        const val NOTIFICATION_NAME = "appName"
        const val NOTIFICATION_CHANNEL = "appName_channel_01"
        private const val scheduleAllAlarms = "ScheduleAllAlarms"

        fun scheduleDailyNotification(delayInDiff: Long, context: Context) {
            val notificationWork = PeriodicWorkRequest.Builder(SetAllAlarmWork::class.java,1,TimeUnit.DAYS)
                .setInitialDelay(delayInDiff, TimeUnit.MILLISECONDS).build()

            val instanceWorkManager = WorkManager.getInstance(context)
            instanceWorkManager.enqueueUniquePeriodicWork(scheduleAllAlarms, ExistingPeriodicWorkPolicy.REPLACE, notificationWork)
        }
    }

    private fun setDailyAlarms(dStore: Preferences?) {

         setAlarmTablets(
            applicationContext,
            CalHelper.breakfastTime(dStore).timeInMillis-System.currentTimeMillis(),
            Constants.BREAKFAST,7
        )


         setAlarmTablets(
            applicationContext,
            CalHelper.lunchTime(dStore).timeInMillis-System.currentTimeMillis(),
            Constants.LUNCH,6
        )


         setAlarmTablets(
            applicationContext,
            CalHelper.eveningTime(dStore).timeInMillis-System.currentTimeMillis(),
            Constants.DINNER,5
        )


         setAlarmTablets(
            applicationContext,
            CalHelper.dinnerTime(dStore).timeInMillis-System.currentTimeMillis(),
            Constants.EVENING,4
        )

    }


    private fun setAlarmTablets(context:Context, timeDiff:Long, dayMealConstant:String, notifyId:Int)
    {
        SetAlarmsWork.scheduleNotification(timeDiff,notifyId,context,dayMealConstant)
    }





}