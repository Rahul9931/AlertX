package com.rahulsaini.alertx.topDialog

import android.app.Activity
import android.app.Application
import com.rahulsaini.alertx.topDialog.message.TopAlertMessage
import com.rahulsaini.alertx.topDialog.builder.AlertBuilder
import com.rahulsaini.alertx.topDialog.config.GlobalConfig
import com.rahulsaini.alertx.topDialog.model.MessageStyle
import com.rahulsaini.alertx.topDialog.utils.QueueManager

object AlertXTop {

    private val globalConfig = GlobalConfig()

    internal fun getGlobalConfigStyle() = globalConfig

    internal fun enqueue(alertMessage: TopAlertMessage){
        QueueManager.enqueue(alertMessage)
    }

    fun initialize(activity: Application, config: GlobalConfig.() -> Unit){
        globalConfig.apply(config)
    }

// Quick Methods
    fun showSuccess(activity: Activity, message: String){
    AlertBuilder(activity)
        .setMessage(message)
        .setSuccess()
        .show()
    }

    fun showInfo(activity: Activity, message: String){
        AlertBuilder(activity)
            .setMessage(message)
            .setInfo()
            .show()
    }

    fun showError(activity: Activity, message: String){
        AlertBuilder(activity)
            .setMessage(message)
            .setError()
            .show()
    }

//    custom message for full control of the style
fun showCustomMessage(activity: Activity, message: String, style: MessageStyle){
    TopAlertMessage(activity,message, style).show()
}
}