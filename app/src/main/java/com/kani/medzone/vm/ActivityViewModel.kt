package com.kani.medzone.vm

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.kani.medzone.db.AppDatabase
import com.kani.medzone.db.Report
import com.kani.medzone.db.TabletEntry
import com.kani.medzone.db.Tablets
import java.util.prefs.Preferences

/**Created by Guru kathir.J on 02,May,2021 **/
class ActivityViewModel(val app: Application) : AndroidViewModel(app) {

    private  var db:AppDatabase?=null
    var tabletsList:MutableLiveData<ArrayList<Tablets>> = MutableLiveData(ArrayList<Tablets>())
    var investigationList:MutableLiveData<ArrayList<Report>> = MutableLiveData(ArrayList())
    var tabEntryList:MutableLiveData<ArrayList<TabletEntry>> = MutableLiveData(ArrayList())
    var datastore:DataStore<androidx.datastore.preferences.core.Preferences>?=null

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

   suspend fun fetchInvestigation()
    {
        investigationList.value?.clear()
        investigationList.postValue(databaseInstance().reportDao().getAll() as ArrayList<Report>?)
    }
    suspend fun fetchTabletEntry()
    {
        tabEntryList.value?.clear()
        tabEntryList.postValue(databaseInstance().tabEntryDao().getAll() as ArrayList<TabletEntry>)

    }

}