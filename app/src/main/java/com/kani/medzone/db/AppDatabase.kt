package com.kani.medzone.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kani.medzone.vm.EventEntry

/**Created by Guru kathir.J on 02,May,2021 **/
@Database(entities = [Tablets::class,TabletEntry::class,Report::class,EventEntry::class], version = 1,exportSchema = false)
@TypeConverters(ReadingsConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tabletsDao(): TabletsDao
    abstract fun tabEntryDao(): TabletEntryDao
    abstract fun reportDao(): ReportDao
    abstract fun eventEntryDao(): EventDao
}