package com.kani.medzone.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.kani.medzone.db.AppDatabase
import com.kani.medzone.db.Tablets

/**Created by Guru kathir.J on 02,May,2021 **/
class ActivityViewModel(val app: Application) : AndroidViewModel(app) {

    private  var db:AppDatabase?=null
    var tabletsList:MutableLiveData<ArrayList<Tablets>> = MutableLiveData(ArrayList<Tablets>())

    fun databaseInstance(): AppDatabase {
        return if (db!=null)
            db as AppDatabase
        else{
            db = Room.databaseBuilder(
                app,
                AppDatabase::class.java, "medZone"
            ).build()
            db as AppDatabase
        }
    }

   suspend fun fetchTabletsList()
    {
        tabletsList.value?.clear()
        tabletsList.postValue(databaseInstance().tabletsDao().getAll() as ArrayList<Tablets>?)
    }
}