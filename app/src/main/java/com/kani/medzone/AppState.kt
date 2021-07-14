package com.kani.medzone

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**Created by Guru kathir.J on 13,July,2021 **/
class AppState: Application() {
    val datastore: DataStore<Preferences> by preferencesDataStore(name = "MedZone_datastore")
    override fun onCreate() {
        super.onCreate()
    }
}