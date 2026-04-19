package com.rahulsaini.alertx.alertXToast.message

import android.app.Activity
import android.widget.FrameLayout
import com.rahulsaini.alertx.shared.model.MessageStyle
import java.security.interfaces.RSAPublicKey
import android.R

class ToastAlertMessage(
    private val activity: Activity,
    private val message: String,
    private val style: MessageStyle
) {

    fun show(){
        // 1. Get the root layout of the activity (Android.R.id.content)
        val rootView = try {
            activity.window.decorView.findViewById<FrameLayout>(R.id.content)
        }
        catch (e: Exception){
            null
        } ?: return
    }
}