package com.kani.medzone

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


        fun breakfastTime(dStore: Preferences?):Calendar
        {
            return  Calendar.getInstance().also {
                it.set(Calendar.HOUR_OF_DAY,dStore?.get(intPreferencesKey(Constants.BREAKFAST_Hour))?:9)
                it.set(Calendar.MINUTE,dStore?.get(intPreferencesKey(Constants.BREAKFAST_min))?:30)
            }

        } fun lunchtTime(dStore: Preferences?):Calendar
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
    }
}

    fun Int.hoursToAM_PM(mins: Int):String
    {
        val value = StringBuilder()
        if(this<12)
        {
            value.append(this)
            value.append(" : ")
            value.append(mins)
            value.append( "AM")
        }else{
            value.append(this-12)
            value.append(" : ")
            value.append(mins)
            value.append( "PM")
        }

        return value.toString()
    }
