package com.baseapp.common.base

import com.baseapp.navigation.NavigationCommand

interface BaseActivityView {
    fun toolbarVisibility(visible: Boolean)
    fun setCustomOnBackPressed(listener: () -> Boolean)
    fun resetOnBackPressed()
    fun changeLanguage(language: String)
    fun navigateModule(command: NavigationCommand)
//    fun checkUpdate(onSuccess: () -> Unit)
}