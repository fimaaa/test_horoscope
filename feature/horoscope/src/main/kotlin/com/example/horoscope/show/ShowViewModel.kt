package com.example.horoscope.show

import androidx.lifecycle.viewModelScope
import com.baseapp.common.base.BaseViewModel
import com.baseapp.model.common.UIText
import com.baseapp.repository.repository.HoroscopeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ShowViewModel @Inject constructor(
    private val horoscopeRepository: HoroscopeRepository
) : BaseViewModel() {

    fun init(dateLong: Long) {
        val selectedDate = Calendar.getInstance()
        selectedDate.timeInMillis = dateLong

        calculateZodiac(selectedDate)
        calculateAge(selectedDate)
    }

    private val _textZodiac: MutableStateFlow<UIText> =
        MutableStateFlow(UIText.DynamicString(""))
    val textZodiac: StateFlow<UIText>
        get() = _textZodiac

    private fun calculateZodiac(selectedDate: Calendar) {
        viewModelScope.launch(Dispatchers.IO) {
            val horoscopeData = horoscopeRepository.getSelectedHoroscope(
                selectedDate.get(Calendar.DAY_OF_MONTH),
                selectedDate.get(Calendar.MONTH)
            )
            _textZodiac.value =
                UIText.DynamicString("Bintang anda adalah ${horoscopeData?.zodiacName ?: "-"}")
        }
    }

    private val _textAge: MutableStateFlow<UIText> =
        MutableStateFlow(UIText.DynamicString(""))
    val textAge: StateFlow<UIText>
        get() = _textAge

    private fun calculateAge(selectedDate: Calendar) {
        val currentDate = Calendar.getInstance()

        var years = currentDate.get(Calendar.YEAR) - selectedDate.get(Calendar.YEAR)
        val months = (currentDate.get(Calendar.MONTH) + 1) - selectedDate.get(Calendar.MONTH)
        val days = currentDate.get(Calendar.DAY_OF_MONTH) - selectedDate.get(Calendar.DAY_OF_MONTH)

        if (days < 0) {
            val lastDayOfMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH)
            currentDate.add(Calendar.MONTH, -1)
            val diff =
                lastDayOfMonth - selectedDate.get(Calendar.DAY_OF_MONTH) + 1 + currentDate.get(
                    Calendar.DAY_OF_MONTH
                )
            selectedDate.add(Calendar.DAY_OF_MONTH, diff)
        }

        if (months < 0) {
            years--
            selectedDate.add(Calendar.YEAR, 1)
        }
        _textAge.value =
            UIText.DynamicString("Usia Saat in adalah $years Tahun, \n $months Bulan, \n $days Hari")
    }
}