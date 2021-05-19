package com.kani.medzone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast




class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
//            val serviceIntent = Intent(context, MyService::class.java)
//            context.startService(serviceIntent)
            Toast.makeText(context.applicationContext, "boot completye", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(context.applicationContext, "Alarm Manager just ran", Toast.LENGTH_LONG)
                .show()
        }
    }

}
