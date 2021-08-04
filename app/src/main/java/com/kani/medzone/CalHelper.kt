package com.kani.medzone

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import androidx.appcompat.content.res.AppCompatResources
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import java.util.*

/**Created by Guru kathir.J on 22,May,2021 **/
class CalHelper {
    companion object{

        fun compareBreakfastTime(dStore: Preferences?):Boolean
        {
           return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)< dStore?.get(intPreferencesKey(Constants.BREAKFAST_Hour))?:9 &&
                    Calendar.getInstance().get(Calendar.MINUTE)< dStore?.get(intPreferencesKey(Constants.BREAKFAST_min))?:30
        }

        fun compareLunch(dStore: Preferences?):Boolean
        {
           return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)< dStore?.get(intPreferencesKey(Constants.LUNCH_Hour))?:13 &&
                    Calendar.getInstance().get(Calendar.MINUTE)< dStore?.get(intPreferencesKey(Constants.LUNCH_min))?:30
        }
        fun compareDinner(dStore: Preferences?):Boolean
        {
           return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)< dStore?.get(intPreferencesKey(Constants.DINNER_Hour))?:20 &&
                    Calendar.getInstance().get(Calendar.MINUTE)< dStore?.get(intPreferencesKey(Constants.DINNER_min))?:30
        }
        fun compareEvening(dStore: Preferences?):Boolean
        {
           return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)< dStore?.get(intPreferencesKey(Constants.EVENING_Hour))?:20 &&
                    Calendar.getInstance().get(Calendar.MINUTE)< dStore?.get(intPreferencesKey(Constants.EVENING_Min))?:30
        }


        fun breakfastTime(dStore: Preferences?):Calendar
        {
            return  Calendar.getInstance().also {
                it.set(Calendar.HOUR_OF_DAY,dStore?.get(intPreferencesKey(Constants.BREAKFAST_Hour))?:9)
                it.set(Calendar.MINUTE,dStore?.get(intPreferencesKey(Constants.BREAKFAST_min))?:30)
            }

        } fun lunchTime(dStore: Preferences?):Calendar
        {
            return  Calendar.getInstance().also {
                it.set(Calendar.HOUR_OF_DAY,dStore?.get(intPreferencesKey(Constants.LUNCH_Hour))?:13)
                it.set(Calendar.MINUTE,dStore?.get(intPreferencesKey(Constants.LUNCH_min))?:30)
            }

        } fun dinnerTime(dStore: Preferences?):Calendar
        {
            return  Calendar.getInstance().also {
                it.set(Calendar.HOUR_OF_DAY,dStore?.get(intPreferencesKey(Constants.DINNER_Hour))?:20)
                it.set(Calendar.MINUTE,dStore?.get(intPreferencesKey(Constants.DINNER_min))?:30)
            }

        }
        fun eveningTime(dStore: Preferences?):Calendar
        {
            return  Calendar.getInstance().also {
                it.set(Calendar.HOUR_OF_DAY,dStore?.get(intPreferencesKey(Constants.EVENING_Hour))?:18)
                it.set(Calendar.MINUTE,dStore?.get(intPreferencesKey(Constants.EVENING_Min))?:30)
            }

        }
    }
}

    fun Int.hoursToAM_PM(mins: Int):String
    {
        val value = StringBuilder()
        if(this<12)
        {
            value.append(this)
            value.append(":")
            value.append(mins)
            value.append( " AM")
        }else{
            value.append(this-12)
            value.append(":")
            value.append(mins)
            value.append( " PM")
        }

        return value.toString()
    }

fun Context.vectorToBitmap(drawableId: Int): Bitmap? {
    val drawable = AppCompatResources.getDrawable( this,drawableId) ?: return null
    val bitmap = createBitmap(
        drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    ) ?: return null
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}
