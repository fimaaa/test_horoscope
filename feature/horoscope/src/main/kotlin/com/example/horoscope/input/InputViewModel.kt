package com.example.horoscope.input

import androidx.lifecycle.viewModelScope
import com.baseapp.common.base.BaseViewModel
import com.baseapp.repository.repository.HoroscopeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor(
    private val horoscopeRepository: HoroscopeRepository
) : BaseViewModel() {
    private val _dateInputted: MutableStateFlow<Long?> =
        MutableStateFlow(null)
    val dateInputted: StateFlow<Long?>
        get() = _dateInputted

    fun setDate(dateInputted: Long?) {
        _dateInputted.value = dateInputted
    }

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            horoscopeRepository.insertAllHoroscope()
        }
    }
}