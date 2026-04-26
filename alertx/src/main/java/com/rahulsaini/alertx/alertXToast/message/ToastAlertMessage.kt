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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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

        animationUp(view)

        scheduleAutoDismiss(view)


    }

    private fun applyStyle(activity: Activity, view: View) {
        var toastContainer = view.findViewById<MaterialCardView>(com.rahulsaini.alertx.R.id.toast_msg_container)
        var toastMsg = view.findViewById<TextView>(com.rahulsaini.alertx.R.id.toast_txt)
        var toastImg = view.findViewById<ImageView>(com.rahulsaini.alertx.R.id.toast_img)

        toastContainer.setCardBackgroundColor(
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
        params.setMargins(20,0,30,100)

        rootView.addView(view, params)
    }

    private fun scheduleAutoDismiss(view: View) {
        val slideDown = try {
            AnimationUtils.loadAnimation(activity, com.rahulsaini.alertx.R.anim.slide_down)
        }
        catch (e: Exception){
            null
        }
        autoDismissHandler = HandlerCompat.createAsync(Looper.getMainLooper())
        autoDismissRunnable = Runnable {
            slideDown?.let {
                view.startAnimation(slideDown)
                slideDown.fillAfter = true
                slideDown.setAnimationListener(object : Animation.AnimationListener{
                    override fun onAnimationEnd(animation: Animation?) {
                        dismissView(view)
                    }

                    override fun onAnimationRepeat(animation: Animation?) {

                    }

                    override fun onAnimationStart(animation: Animation?) {

                    }

                })
            }
        }
        autoDismissHandler?.postDelayed(autoDismissRunnable!!, style.duration)
    }

    fun dismissView(view: View){
        (view.parent as ViewGroup).removeView(view)
    }

    fun animationUp(view: View){
        try {
            val slideUp = AnimationUtils.loadAnimation(activity, com.rahulsaini.alertx.R.anim.slide_up)
            // fillAfter ko true karein taaki animation ke baad view wahi ruka rahe
            slideUp.fillAfter = true

            // Animation smooth dikhane ke liye interpolator
            slideUp.interpolator = android.view.animation.DecelerateInterpolator()
            view.startAnimation(slideUp)
        }
        catch (e: Exception){
            null
        }
    }
}