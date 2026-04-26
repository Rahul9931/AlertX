package com.rahulsaini.alertx.alertXToast

import android.app.Activity
import com.rahulsaini.alertx.alertXToast.builder.AlertBuilder
import com.rahulsaini.alertx.shared.config.GlobalConfig

object AlertXToast {
    private val globalConfig = GlobalConfig()

    internal fun getGlobalConfig() = globalConfig

    fun showSuccessToast(activity: Activity, message: String){
        AlertBuilder(activity)
            .setMessage(message)
            .setSuccess()
            .show()
    }
}