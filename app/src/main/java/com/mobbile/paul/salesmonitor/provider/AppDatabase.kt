package com.mobbile.paul.salesmonitor.provider

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mobbile.paul.salesmonitor.model.Questions


@Database(entities = [Questions::class],version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val appdao: AppDao

    companion object {
        val DATABASE_NAME = "rep_mobile_trader"
    }
}