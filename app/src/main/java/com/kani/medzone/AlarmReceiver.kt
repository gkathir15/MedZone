package com.kani.medzone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

//        val serviceIntent = Intent(context, MedService::class.java)
//        intent.extras?.let {serviceIntent.putExtras(it)}
//        context.startService( serviceIntent)

        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            GlobalScope.launch(Dispatchers.IO) {
                val   dStore =  (context.applicationContext as AppState).datastore.data.first().toPreferences()
                launch (Dispatchers.Main){
                    AlarmManagerHelper.setAlarmForSync(context)
                    AlarmManagerHelper.setDailyAlarms(dStore,context)
                }
            }
        } else
            if (intent.getStringExtra(Constants.callFOR).equals(Constants.TABLET_ALARM)) {
                intent.getStringExtra(Constants.callFOR)?.let {
                    NotificationObj.sendNotification(
                        intent.getIntExtra("ID", 0), it,
                        intent.getStringExtra(Constants.DURATION)!!, context
                    )
                }


            } else if (intent.getStringExtra(Constants.callFOR).equals(Constants.SYNC))
            {
                GlobalScope.launch(Dispatchers.IO) {
                    val   dStore =  (context.applicationContext as AppState).datastore.data.first().toPreferences()
                    launch (Dispatchers.Main){
                        AlarmManagerHelper.setAlarmForSync(context)
                        AlarmManagerHelper.setDailyAlarms(dStore,context)
                    }
                }
            }



    }

}
