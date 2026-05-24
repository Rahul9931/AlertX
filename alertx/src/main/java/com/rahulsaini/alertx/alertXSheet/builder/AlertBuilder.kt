package com.rahulsaini.alertx.alertXSheet.builder

import android.app.Activity
import android.util.Log
import com.rahulsaini.alertx.R
import com.rahulsaini.alertx.alertXSheet.AlertXSheet
import com.rahulsaini.alertx.alertXSheet.message.SheetAlertMessage
import com.rahulsaini.alertx.shared.model.AlertAnimationType
import com.rahulsaini.alertx.shared.model.AlertPosition
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

    fun setPosition(position: AlertPosition) = apply {
        val baseStyle = customStyle ?: getStyleFromType()
        Log.d("AlertX_Debug", "setPosition: Position set to $position")
        this.customStyle = baseStyle.copy(position = position)
        Log.d("AlertX_Debug", "custom style -> ${this.customStyle}")
    }

    fun setAnimation(animation: AlertAnimationType) = apply {
        val baseStyle = customStyle ?: getStyleFromType()
        this.customStyle = baseStyle.copy(animationType = animation)
    }

    fun setWarning() = apply {
        this.type = MessageType.WARNING
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


        AlertXSheet.enqueue(SheetAlertMessage(activity, message, styleWithIcon))
    }

    fun getStyleFromType(): MessageStyle {

        customStyle?.let { return it }

        return when(type){
            MessageType.CUSTOM -> customStyle!!
            MessageType.SUCCESS -> AlertXSheet.getGlobalConfigStyle().successStyle
            MessageType.WARNING -> AlertXSheet.getGlobalConfigStyle().warningStyle
            MessageType.INFO -> AlertXSheet.getGlobalConfigStyle().infoStyle
            MessageType.ERROR -> AlertXSheet.getGlobalConfigStyle().errorStyle
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