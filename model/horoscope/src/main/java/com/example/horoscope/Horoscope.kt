package com.example.horoscope

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "horoscope")
data class Horoscope(
    @PrimaryKey
    @SerializedName("zodiac_name") val zodiacName: String,
    @SerializedName("date_start") val dateStart: Long,
    @SerializedName("date_end") val dateEnd: Long
)
