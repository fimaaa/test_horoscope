package com.baseapp.repository.repository

import com.baseapp.common.BaseIODispatcher
import com.baseapp.local.dao.HoroscopeDao
import com.baseapp.repository.common.Repository
import com.example.horoscope.Horoscope
import kotlinx.coroutines.CoroutineDispatcher
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

interface HoroscopeRepository : Repository {
    suspend fun insertAllHoroscope()

    suspend fun getSelectedHoroscope(selectedDay: Int, selectedMonth: Int): Horoscope?
}

@Singleton
class HoroscopeRepositoryImpl @Inject constructor(
    private val horoscopeDao: HoroscopeDao,
    @BaseIODispatcher private val ioDispatcher: CoroutineDispatcher
) : HoroscopeRepository {
    init {
        Timber.i("Injection SessionRepository")
    }

    override suspend fun insertAllHoroscope() {
        // Define the start and end dates for each zodiac sign as Long timestamps (in milliseconds since epoch)
        val ariesdateStart = getTimestamp(3, 21) // March 21
        val ariesEndDate = getTimestamp(4, 19) // April 19

        val taurusdateStart = getTimestamp(4, 20) // April 20
        val taurusEndDate = getTimestamp(5, 20) // May 20

        val geminidateStart = getTimestamp(5, 21) // May 21
        val geminiEndDate = getTimestamp(6, 20) // June 20

        val cancerdateStart = getTimestamp(6, 21) // June 21
        val cancerEndDate = getTimestamp(7, 22) // July 22

        val leodateStart = getTimestamp(7, 23) // July 23
        val leoEndDate = getTimestamp(8, 22) // August 22

        val virgodateStart = getTimestamp(8, 23) // August 23
        val virgoEndDate = getTimestamp(9, 22) // September 22

        val libradateStart = getTimestamp(9, 23) // September 23
        val libraEndDate = getTimestamp(10, 22) // October 22

        val scorpiodateStart = getTimestamp(10, 23) // October 23
        val scorpioEndDate = getTimestamp(11, 21) // November 21

        val sagittariusdateStart = getTimestamp(11, 22) // November 22
        val sagittariusEndDate = getTimestamp(12, 21) // December 21

        val capricorndateStart = getTimestamp(12, 22) // December 22
        val capricornEndDate = getTimestamp(1, 19) // January 19

        val aquariusdateStart = getTimestamp(1, 20) // January 20
        val aquariusEndDate = getTimestamp(2, 18) // February 18

        val piscesdateStart = getTimestamp(2, 19) // February 19
        val piscesEndDate = getTimestamp(3, 20) // March 20

        val zodiacSigns = listOf(
            Horoscope(zodiacName = "Aries", dateStart = ariesdateStart, dateEnd = ariesEndDate),
            Horoscope(zodiacName = "Taurus", dateStart = taurusdateStart, dateEnd = taurusEndDate),
            Horoscope(zodiacName = "Gemini", dateStart = geminidateStart, dateEnd = geminiEndDate),
            Horoscope(zodiacName = "Cancer", dateStart = cancerdateStart, dateEnd = cancerEndDate),
            Horoscope(zodiacName = "Leo", dateStart = leodateStart, dateEnd = leoEndDate),
            Horoscope(zodiacName = "Virgo", dateStart = virgodateStart, dateEnd = virgoEndDate),
            Horoscope(zodiacName = "Libra", dateStart = libradateStart, dateEnd = libraEndDate),
            Horoscope(
                zodiacName = "Scorpio",
                dateStart = scorpiodateStart,
                dateEnd = scorpioEndDate
            ),
            Horoscope(
                zodiacName = "Sagittarius",
                dateStart = sagittariusdateStart,
                dateEnd = sagittariusEndDate
            ),
            Horoscope(
                zodiacName = "Capricorn",
                dateStart = capricorndateStart,
                dateEnd = capricornEndDate
            ),
            Horoscope(
                zodiacName = "Aquarius",
                dateStart = aquariusdateStart,
                dateEnd = aquariusEndDate
            ),
            Horoscope(zodiacName = "Pisces", dateStart = piscesdateStart, dateEnd = piscesEndDate)
        )
        horoscopeDao.insert(zodiacSigns)
    }

    // Function to calculate the timestamp for a given month and day
    private fun getTimestamp(month: Int, day: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month - 1) // Calendar months are 0-based
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.YEAR, 0)
        return calendar.timeInMillis
    }

    override suspend fun getSelectedHoroscope(selectedDay: Int, selectedMonth: Int): Horoscope? {
        return horoscopeDao.getZodiacSign(getTimestamp(selectedMonth, selectedDay))
    }
}