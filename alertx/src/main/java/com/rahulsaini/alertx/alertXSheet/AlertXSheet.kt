package com.rahulsaini.alertx.alertXSheet

import android.app.Activity
import android.app.Application
import com.rahulsaini.alertx.alertXSheet.message.SheetAlertMessage
import com.rahulsaini.alertx.alertXSheet.builder.AlertBuilder
import com.rahulsaini.alertx.shared.config.GlobalConfig
import com.rahulsaini.alertx.shared.model.MessageStyle
import com.rahulsaini.alertx.alertXSheet.utils.QueueManager

object AlertXSheet {

    private val globalConfig = GlobalConfig()

    internal fun getGlobalConfigStyle() = globalConfig

    internal fun enqueue(alertMessage: SheetAlertMessage){
        QueueManager.enqueue(alertMessage)
    }

    fun initialize(activity: Application, config: GlobalConfig.() -> Unit){
        globalConfig.apply(config)
    }

    fun dismissCurrentAlert(){
        QueueManager.dismissCurrentAlert()
    }

// Quick Methods
    fun showSuccessSheet(activity: Activity, message: String): AlertBuilder{
        return AlertBuilder(activity)
        .setMessage(message)
        .setSuccess()
    }

    fun showWarningSheet(activity: Activity, message: String): AlertBuilder{
        return AlertBuilder(activity)
            .setMessage(message)
            .setWarning()
    }

    fun showInfoSheet(activity: Activity, message: String): AlertBuilder{
        return AlertBuilder(activity)
            .setMessage(message)
            .setInfo()
    }

    fun showErrorSheet(activity: Activity, message: String): AlertBuilder{
        return AlertBuilder(activity)
            .setMessage(message)
            .setError()
    }

//    custom message for full control of the style
fun showCustomSheet(activity: Activity, message: String, style: MessageStyle){
    AlertBuilder(activity)
        .setMessage(message)
        .setCustomStyle(style)
        .show()
}
}