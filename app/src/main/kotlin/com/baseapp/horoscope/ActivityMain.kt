package com.baseapp.horoscope

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.baseapp.common.base.BaseBindingActivity
import com.baseapp.common.extension.changeStatusBarColor
import com.baseapp.common.extension.gone
import com.baseapp.common.extension.visible
import com.baseapp.common.helper.PermissionHelper.getPermission
import com.baseapp.horoscope.databinding.ActivityMainBinding
import com.baseapp.navigation.MainNavDirections
import com.baseapp.navigation.NavigationCommand
import com.baseapp.common.R as commonR
import com.baseapp.navigation.R as navR
import com.example.horoscope.R as horoscopeR

class ActivityMain : BaseBindingActivity<ActivityMainBinding>() {
    private lateinit var parentController: NavController

    private val mAppBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                horoscopeR.id.inputFragment
            ), // Masukin ID Fragment yang Navigation Icon is Burger Menu,
            binding.drawerLayout
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        var isGranted = true
        result.values.forEach {
            if (!it) isGranted = false
        }
        if (isGranted) {
            grantedAllPermission()
        } else {
            askNotificationPermission(true)
        }
    }

    override fun navigateModule(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.To -> findNavController(R.id.nav_host_fragment).navigate(
                command.directions
            )

            is NavigationCommand.Back -> findNavController(R.id.nav_host_fragment).navigateUp()
            is NavigationCommand.ManualTo -> findNavController(R.id.nav_host_fragment).navigate(
                command.directionId,
                command.bundle
            )
        }
    }

    override fun onInitialization() {
//        onUnAuthorized = {
//            findNavController(R.id.nav_host_fragment).navigate(
//                MainNavDirections.navigateToHoroscope()
//            )
//        }

        changeStatusBarColor(commonR.color.color_primary)

        askNotificationPermission()
    }

    private fun customActionFragment(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ): Boolean {
        return when (destination.id) {
            horoscopeR.id.inputFragment -> {
                toolbarVisibility(false)
                true
            }

            else -> false
        }
    }

    private fun grantedAllPermission() {
        setTheme(R.style.Theme_BaseDagger)
        super.onInitialization()
        settingUpFragment()
    }

    private fun askNotificationPermission(
        isShowDialog: Boolean = false
    ) {
        val listPermission = mutableListOf<String>(
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.CAMERA
        )
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
//            listPermission.addAll(
//                listOf(
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                )
//            )
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            listPermission.add(Manifest.permission.POST_NOTIFICATIONS)
//        }
        getPermission(requestPermissionLauncher,
            listPermission = listPermission.toTypedArray(),
            isShowDialog = isShowDialog,
            onGranted = {
                grantedAllPermission()
            })
    }

    private fun settingUpFragment() {
        binding.layoutNavFooter.tvLogout.setOnClickListener {
            navigateModule(
                NavigationCommand.To(
                    MainNavDirections.navigateToHoroscope()
                )
            )
        }
        findNavController(R.id.nav_host_fragment).setGraph(navR.navigation.main_nav)
        setSupportActionBar(binding.topAppBar)
//        val navController: NavController =
//            Navigation.findNavController(this, R.id.nav_host_fragment)
        parentController = findNavController(R.id.nav_host_fragment).apply {
            // Set up ActionBar
            setupActionBarWithNavController(this, binding.drawerLayout)
            // Set up NavigationView
            binding.navView.setupWithNavController(this)
        }
        NavigationUI.setupActionBarWithNavController(
            this, parentController, mAppBarConfiguration
        )
        NavigationUI.setupWithNavController(binding.navView, parentController)
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { controller, destination, argument ->
            if (destination == null) {
                // Navigate to a default/fallback destination
                findNavController(R.id.nav_host_fragment).navigate(horoscopeR.id.inputFragment)
            }
            // Buat CUstom tombol dikiri / mau custom toolbar
            if (!customActionFragment(controller, destination, argument)) {
                binding.topAppBar.visible()
                binding.topAppBarLogo.gone()
                binding.topAppBar.setNavigationOnClickListener {
                    parentController.navigateUp()
                }
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(
            navController, mAppBarConfiguration
        ) || super.onSupportNavigateUp()
    }
}