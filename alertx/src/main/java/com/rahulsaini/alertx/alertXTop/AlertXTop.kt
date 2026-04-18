package com.rahulsaini.alertx.alertXTop

import android.app.Activity
import android.app.Application
import com.rahulsaini.alertx.alertXTop.message.TopAlertMessage
import com.rahulsaini.alertx.alertXTop.builder.AlertBuilder
import com.rahulsaini.alertx.alertXTop.config.GlobalConfig
import com.rahulsaini.alertx.shared.model.MessageStyle
import com.rahulsaini.alertx.alertXTop.utils.QueueManager

object AlertXTop {

    private val globalConfig = GlobalConfig()

    internal fun getGlobalConfigStyle() = globalConfig

    internal fun enqueue(alertMessage: TopAlertMessage){
        QueueManager.enqueue(alertMessage)
    }

    fun initialize(activity: Application, config: GlobalConfig.() -> Unit){
        globalConfig.apply(config)
    }

    fun dismissCurrentAlert(){
        QueueManager.dismissCurrentAlert()
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
    AlertBuilder(activity)
        .setMessage(message)
        .setCustomStyle(style)
        .show()
}
}