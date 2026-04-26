package com.rahulsaini.alertx.alertXToast.message

import android.app.Activity
import android.widget.FrameLayout
import com.rahulsaini.alertx.shared.model.MessageStyle
import java.security.interfaces.RSAPublicKey
import android.R
import android.os.Handler
import android.os.Looper
import android.print.PrintAttributes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.HandlerCompat
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.Runnable

class ToastAlertMessage(
    private val activity: Activity,
    private val message: String,
    private val style: MessageStyle
) {

    private var autoDismissHandler: Handler? = null
    private var autoDismissRunnable: Runnable? = null

    fun show(){
        // 1. Get the root layout of the activity (Android.R.id.content)
        val rootView = try {
            activity.window.decorView.findViewById<FrameLayout>(R.id.content)
        }
        catch (e: Exception){
            null
        } ?: return

        // 2. create the view with parent context to respect xml layout attribute
        var view = createView(activity, rootView)

        // 3. apply text and style
        applyStyle(activity,view)

        addToScreen(view,rootView)

        scheduleToDismiss(view)


    }

    private fun applyStyle(activity: Activity, view: View) {
        var toastContainer = view.findViewById<MaterialCardView>(com.rahulsaini.alertx.R.id.toast_msg_container)
        var toastMsg = view.findViewById<TextView>(com.rahulsaini.alertx.R.id.toast_txt)
        var toastImg = view.findViewById<ImageView>(com.rahulsaini.alertx.R.id.toast_img)

        toastContainer.setBackgroundColor(
            ContextCompat.getColor(activity, style.containerBackgroundColorRes)
        )
        toastMsg.text = message
        toastMsg.setTextColor(style.textColor)

        toastImg.apply {
            if (style.showIcon && style.iconResource != null){
                isVisible = true
                setImageResource(style.iconResource)
                setColorFilter(style.iconTint)
                contentDescription = "icon image"
            }
            else{
                isVisible = false
            }
        }
    }

    private fun createView(activity: Activity, rootView: ViewGroup): View {
        var inflator = LayoutInflater.from(activity)
        return inflator.inflate(com.rahulsaini.alertx.R.layout.toast_message, rootView, false)
    }

    private fun addToScreen(view: View, rootView: FrameLayout){
        var params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.BOTTOM
//        params.bottomMargin = 100
        params.setMargins(10,0,10,20)

        rootView.addView(view, params)
    }

    private fun scheduleToDismiss(view: View) {
        autoDismissHandler = HandlerCompat.createAsync(Looper.getMainLooper())
        autoDismissRunnable = Runnable {
            dismissView(view)
        }
        autoDismissHandler?.postDelayed(autoDismissRunnable!!, style.duration)
    }

    fun dismissView(view: View){
        (view.parent as ViewGroup).removeView(view)
    }
}