package com.rahulsaini.alertx.alertXToast.builder

import android.app.Activity
import com.rahulsaini.alertx.R
import com.rahulsaini.alertx.alertXToast.AlertXToast
import com.rahulsaini.alertx.alertXToast.message.ToastAlertMessage
import com.rahulsaini.alertx.shared.model.MessageStyle
import com.rahulsaini.alertx.shared.model.MessageType

class AlertBuilder(private val activity: Activity) {
    private var customStyle: MessageStyle? = null
    private var type: MessageType = MessageType.INFO
    private var message: String = ""

    fun setMessage(message:String) = apply{
        this.message = message
    }

    fun setSuccess() = apply {
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
        var style = getStyleFromType()
        var styleWithIcon = MessageStyle()
        if (style.showIcon){
            var icon = style.iconResource ?: getDefaultIconFromType()
            styleWithIcon = style.copy(
                iconResource = icon
            )
        }
        else{
            styleWithIcon = style
        }

        AlertXToast.enqueue(ToastAlertMessage(activity, message, styleWithIcon))
    }

    fun getStyleFromType(): MessageStyle{
        return when(type){
            MessageType.SUCCESS -> AlertXToast.getGlobalConfig().successStyle
            MessageType.INFO -> AlertXToast.getGlobalConfig().infoStyle
            MessageType.ERROR -> AlertXToast.getGlobalConfig().errorStyle
            MessageType.CUSTOM -> customStyle!!
        }
    }

    fun getDefaultIconFromType(): Int{
        return when(type){
            MessageType.SUCCESS -> R.drawable.check_circle_24
            MessageType.INFO -> R.drawable.info_24
            MessageType.ERROR -> R.drawable.error_24
            MessageType.CUSTOM -> R.drawable.info_24
        }
    }
}