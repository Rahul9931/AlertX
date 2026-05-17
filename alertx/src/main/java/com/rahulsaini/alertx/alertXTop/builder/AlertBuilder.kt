package com.rahulsaini.alertx.alertXTop.builder

import android.app.Activity
import com.rahulsaini.alertx.R
import com.rahulsaini.alertx.alertXTop.AlertXTop
import com.rahulsaini.alertx.alertXTop.message.TopAlertMessage
import com.rahulsaini.alertx.shared.model.MessageStyle
import com.rahulsaini.alertx.shared.model.MessageType

class AlertBuilder(private val activity: Activity){
    private var message: String = ""
    private var customStyle: MessageStyle? = null
    private var type = MessageType.INFO

    fun setMessage(message:String) = apply{
        this.message = message
    }

    fun setSuccess() = apply {
        this.type = MessageType.SUCCESS
        this.customStyle = null
    }

    fun setWarning() = apply {
        this.type = MessageType.SUCCESS
        this.customStyle = null
    }

    fun setInfo() = apply {
        this.type = MessageType.INFO
        this.customStyle = null
    }

    fun setError() = apply {
        this.type = MessageType.ERROR
        this.customStyle = null
    }

    fun setCustomStyle(style: MessageStyle) = apply {
        this.type = MessageType.CUSTOM
        this.customStyle = style
    }

    fun show(){
        val finalStyle = getStyleFromType()
        var styleWithIcon: MessageStyle
        if (finalStyle.showIcon){
            val finalIcon = finalStyle.iconResource ?: getDefaultIconForType()
            styleWithIcon = finalStyle.copy(
                iconResource = finalIcon
            )
        }
        else{
            styleWithIcon = finalStyle
        }


        AlertXTop.enqueue(TopAlertMessage(activity, message, styleWithIcon))
    }

    fun getStyleFromType(): MessageStyle {
        return when(type){
            MessageType.CUSTOM -> customStyle!!
            MessageType.SUCCESS -> AlertXTop.getGlobalConfigStyle().successStyle
            MessageType.WARNING -> AlertXTop.getGlobalConfigStyle().warningStyle
            MessageType.INFO -> AlertXTop.getGlobalConfigStyle().infoStyle
            MessageType.ERROR -> AlertXTop.getGlobalConfigStyle().errorStyle
        }
    }

    fun getDefaultIconForType(): Int{
        return when(type){
            MessageType.SUCCESS -> R.drawable.check_circle_24
            MessageType.WARNING -> R.drawable.info_24
            MessageType.INFO -> R.drawable.info_24
            MessageType.ERROR -> R.drawable.error_24
            MessageType.CUSTOM -> R.drawable.info_24
        }
    }

}