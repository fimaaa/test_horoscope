package com.baseapp.horoscope

import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.baseapp.common.base.BaseBindingActivity
import com.baseapp.horoscope.databinding.ActivityCrashBinding
import com.baseapp.common.R as commonR
import javax.inject.Inject

class CrashActivity : BaseBindingActivity<ActivityCrashBinding>() {

    @Inject
    lateinit var caocConfig: CaocConfig.Builder

    override fun onInitialization() {
        super.onInitialization()
        val config: CaocConfig =
            CustomActivityOnCrash.getConfigFromIntent(intent) ?: caocConfig.get()

        binding.blankLayout.setText(
            if (BuildConfig.VARIANT != "release") {
                getString(commonR.string.error_default)
            } else {
                CustomActivityOnCrash.getAllErrorDetailsFromIntent(this, intent)
            }
        )

        binding.blankLayout.setDesc(
            if (BuildConfig.VARIANT != "release") {
                CustomActivityOnCrash.getStackTraceFromIntent(intent)
            } else {
                CustomActivityOnCrash.getAllErrorDetailsFromIntent(this, intent)
            }
        )

        if (config.isShowRestartButton && config.restartActivityClass != null) {
            binding.blankLayout.setOnClick("Restart App") {
                CustomActivityOnCrash.restartApplication(
                    this,
                    config
                )
            }
        } else {
            binding.blankLayout.setOnClick("Close App") {
                CustomActivityOnCrash.closeApplication(
                    this,
                    config
                )
            }
        }
    }
}