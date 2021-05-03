package com.kani.medzone

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.kani.medzone.db.AppDatabase
import com.kani.medzone.db.Tablets

/**Created by Guru kathir.J on 02,May,2021 **/
class ActivityViewModel(val app: Application) : AndroidViewModel(app) {

    private var db:AppDatabase?=null
    var tabletsList:MutableLiveData<List<Tablets>>?=null

    fun databaseInstance(): AppDatabase {
        if (db!=null)
        return db as AppDatabase
        else{
            db = Room.databaseBuilder(
                    app,
                    AppDatabase::class.java, "medZone"
            ).build()
            return db as AppDatabase
        }
    }

    fun fetchTabletsList()
    {
        tabletsList?.postValue(databaseInstance().tabletsDao().getAll())
    }
}