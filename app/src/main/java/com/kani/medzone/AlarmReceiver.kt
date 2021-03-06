package com.kani.medzone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast




class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, MedService::class.java)
        intent.extras?.let {serviceIntent.putExtras(it)}
        context.startService( serviceIntent)
        Toast.makeText(context.applicationContext, "Tablet notification", Toast.LENGTH_LONG)
            .show()
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {

            Toast.makeText(context.applicationContext, "boot completye", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(context.applicationContext, "Alarm Manager just ran", Toast.LENGTH_LONG)
                .show()
        }
    }

}
