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
    private var lastStyleType = MessageType.INFO

    fun setMessage(message:String) = apply{
        this.message = message
    }

    fun setSuccess() = apply {
        this.type = MessageType.SUCCESS
        this.customStyle = null
        this.lastStyleType = MessageType.SUCCESS
    }

    fun setInfo() = apply {
        this.type = MessageType.INFO
        this.customStyle = null
        this.lastStyleType = MessageType.INFO
    }

    fun setError() = apply {
        this.type = MessageType.ERROR
        this.customStyle = null
        this.lastStyleType = MessageType.ERROR
    }

    fun setCustomStyle(style: MessageStyle) = apply {
        this.type = MessageType.CUSTOM
        this.customStyle = style
        this.lastStyleType = MessageType.CUSTOM
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
            MessageType.INFO -> AlertXTop.getGlobalConfigStyle().infoStyle
            MessageType.ERROR -> AlertXTop.getGlobalConfigStyle().errorStyle
            else -> AlertXTop.getGlobalConfigStyle().infoStyle
        }
    }

    fun getDefaultIconForType(): Int{
        return when(type){
            MessageType.SUCCESS -> R.drawable.check_circle_24
            MessageType.INFO -> R.drawable.info_24
            MessageType.ERROR -> R.drawable.error_24
            MessageType.CUSTOM -> R.drawable.info_24
        }
    }

}