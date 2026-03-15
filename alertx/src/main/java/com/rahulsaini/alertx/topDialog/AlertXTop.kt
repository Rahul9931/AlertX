package com.rahulsaini.alertx.topDialog

import android.R
import android.app.Activity
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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

    fun initialize(activity: Activity, config: GlobalConfig.()-> Unit){
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