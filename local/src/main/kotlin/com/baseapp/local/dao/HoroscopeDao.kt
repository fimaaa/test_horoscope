package com.baseapp.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.baseapp.local.common.dao.BaseDao
import com.example.horoscope.Horoscope

@Dao
interface HoroscopeDao : BaseDao<Horoscope> {
    @Query("SELECT * FROM Horoscope WHERE :inputDate >= dateStart AND :inputDate <= dateEnd")
    suspend fun getZodiacSign(inputDate: Long): Horoscope?
}