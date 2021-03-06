package com.kani.medzone

import android.content.Context
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
          val   dStore =  (context.applicationContext as AppState).datastore.data.first().toPreferences()
            launch (Dispatchers.Main){
                AlarmManagerHelper.setDailyAlarms(dStore,context)
            }
        }
        return success()
    }



    companion object {
        private const val scheduleAllAlarms = "ScheduleAllAlarms"

        fun scheduleDailyNotification(delayInDiff: Long, context: Context) {
            val notificationWork = PeriodicWorkRequest.Builder(SetAllAlarmWork::class.java,1,TimeUnit.DAYS)
                .setInitialDelay(delayInDiff, TimeUnit.MILLISECONDS).build()

            val instanceWorkManager = WorkManager.getInstance(context)
            instanceWorkManager.enqueueUniquePeriodicWork(scheduleAllAlarms, ExistingPeriodicWorkPolicy.KEEP, notificationWork)
        }
    }










}