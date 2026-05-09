package com.rahulsaini.alertx.alertXToast

import android.app.Activity
import com.rahulsaini.alertx.alertXToast.builder.AlertBuilder
import com.rahulsaini.alertx.alertXToast.message.ToastAlertMessage
import com.rahulsaini.alertx.alertXToast.utils.QueueManager
import com.rahulsaini.alertx.shared.config.GlobalConfig
import com.rahulsaini.alertx.shared.model.MessageStyle

object AlertXToast {
    private val globalConfig = GlobalConfig()

    internal fun getGlobalConfig() = globalConfig

    internal fun enqueue(alertMessage: ToastAlertMessage){
        QueueManager.enqueue(alertMessage)
    }

    fun showSuccessToast(activity: Activity, message: String){
        AlertBuilder(activity)
            .setMessage(message)
            .setSuccess()
            .show()
    }

    fun showInfoToast(activity: Activity, message: String){
        AlertBuilder(activity)
            .setMessage(message)
            .setInfo()
            .show()
    }

    fun showErrorToast(activity: Activity, message: String){
        AlertBuilder(activity)
            .setMessage(message)
            .setError()
            .show()
    }

    fun showCustomMessage(activity: Activity, message: String, style: MessageStyle){
        AlertBuilder(activity)
            .setMessage(message)
            .setCustomStyle(style)
            .show()
    }
}