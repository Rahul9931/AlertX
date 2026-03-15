package com.rahulsaini.alertx.topDialog.builder

import android.app.Activity
import com.rahulsaini.alertx.R
import com.rahulsaini.alertx.topDialog.AlertXTop
import com.rahulsaini.alertx.topDialog.message.TopAlertMessage
import com.rahulsaini.alertx.topDialog.model.MessageStyle
import com.rahulsaini.alertx.topDialog.model.MessageType

class AlertBuilder(private val activity: Activity){
    private var message: String = ""
    private var customStyle: MessageStyle? = null
    private var type = MessageType.INFO

    fun setMessage(message:String) = apply{
        this.message = message
    }

    fun setSuccess() = apply {
        this.type = MessageType.SUCCESS
    }

    fun setInfo() = apply {
        this.type = MessageType.INFO
    }

    fun setError() = apply {
        this.type = MessageType.ERROR
    }

    fun show(){
        val finalStyle = customStyle ?: getStyleFromType()
        val finalIcon = finalStyle.iconResource ?: getDefaultIconForType()
        val styleWithIcon = finalStyle.copy(
            iconResource = finalIcon
        )

        AlertXTop.enqueue(TopAlertMessage(activity, message, styleWithIcon))
    }

    fun getStyleFromType(): MessageStyle {
        return when(type){
            MessageType.SUCCESS -> AlertXTop.getGlobalConfigStyle().successStyle
            MessageType.INFO -> AlertXTop.getGlobalConfigStyle().infoStyle
            MessageType.ERROR -> AlertXTop.getGlobalConfigStyle().errorStyle
        }
    }

    fun getDefaultIconForType(): Int{
        return when(type){
            MessageType.SUCCESS -> R.drawable.check_circle_24
            MessageType.INFO -> R.drawable.info_24
            MessageType.ERROR -> R.drawable.error_24
        }
    }

}