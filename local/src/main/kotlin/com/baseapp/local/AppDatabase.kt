package com.baseapp.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.baseapp.local.converter.Converters
import com.baseapp.local.dao.HoroscopeDao
import com.example.horoscope.Horoscope

@Database(
    entities = [
        Horoscope::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getHoroscopeDao(): HoroscopeDao
}