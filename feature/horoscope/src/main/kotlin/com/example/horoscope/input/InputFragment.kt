package com.example.horoscope.input

import android.app.DatePickerDialog
import com.baseapp.common.base.BaseBindingFragment
import com.baseapp.common.extension.Toast_Error
import com.baseapp.common.extension.safeCollect
import com.baseapp.common.extension.showSnackBar
import com.example.horoscope.databinding.FragmentHoroscopeInputBinding
import java.util.Calendar

class InputFragment : BaseBindingFragment<FragmentHoroscopeInputBinding, InputViewModel>() {
    override fun onInitialization(binding: FragmentHoroscopeInputBinding) {
        super.onInitialization(binding)
        viewModel.init()
    }

    override fun onReadyAction() {
        binding.tvDateSubmit.setOnClickListener {
            showDatePickerDialog()
        }
        binding.btnSubmit.setOnClickListener {
            viewModel.dateInputted.value?.let {
                viewModel.navigate(
                    InputFragmentDirections.actionInputFragmentToShowFragment(
                        it,
                        binding.etNameSubmit.text.toString()
                    )
                )
            } ?: run {
                requireContext().showSnackBar(binding.root, "date cannot empty", Toast_Error)
            }
        }
    }

    override fun onObserveAction() {
        super.onObserveAction()
        safeCollect(viewModel.dateInputted) {
            it?.let {
                val selectedDate = Calendar.getInstance()
                selectedDate.timeInMillis = it
                val selectedMonth = selectedDate.get(Calendar.MONTH)
                val selectedDay = selectedDate.get(Calendar.DAY_OF_MONTH)
                val selectedYear = selectedDate.get(Calendar.YEAR)

                // Do something with the selected date and month
                // For example, display it in a TextView
                binding.tvDateSubmit.text = "$selectedDay/$selectedMonth/$selectedYear"
            }
        }
    }

    private fun showDatePickerDialog() {
        val currentDate = Calendar.getInstance()
        // Get the current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a DatePickerDialog and set a listener for date selection
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Handle the selected date here
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth + 1, selectedDay)

                if (selectedDate.after(currentDate)) {
                    // The selected date is in the future
                    // You can handle this situation here, for example, by showing an error message
                    // or resetting the date to the current date
                    val errorMessage = "Please select a date in the past or today."
                    requireContext().showSnackBar(binding.root, errorMessage, Toast_Error)
                    // Reset the date to the current date
                    viewModel.setDate(null)
                } else {
                    // The selected date is valid
                    viewModel.setDate(selectedDate.timeInMillis)
                }
            },
            year,
            month,
            day
        )

        // Set the maximum date allowed as today's date
        datePickerDialog.datePicker.maxDate = currentDate.timeInMillis

        // Show the DatePickerDialog
        datePickerDialog.show()
    }
}