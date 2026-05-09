package com.rahulsaini.alertx.alertXToast.message

import android.app.Activity
import android.widget.FrameLayout
import com.rahulsaini.alertx.shared.model.MessageStyle
import android.R
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
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
        val rootView = try {
            activity.window.decorView.findViewById<FrameLayout>(R.id.content)
        }
        catch (e: Exception){
            null
        } ?: return

        val view = createView(activity, rootView)
        applyStyle(activity, view)
        addToScreen(view, rootView)
        
        // Setup click listener
        dismissOnTouch(view)

        // Start property animation (Hit-box safe)
        animationUp(view) {
            // Animation finished
        }

        scheduleAutoDismiss(view)
    }

    private fun dismissOnTouch(view: View) {
        view.setOnClickListener {
            Log.d("check_toast", "Toast clicked! Animating down...")
            // Pehle animate down karein, phir remove karein
            animateDown(view) {
                dismissView(view)
            }
        }
    }

    private fun applyStyle(activity: Activity, view: View) {
        val toastContainer = view.findViewById<MaterialCardView>(com.rahulsaini.alertx.R.id.toast_msg_container)
        val toastMsg = view.findViewById<TextView>(com.rahulsaini.alertx.R.id.toast_txt)
        val toastImg = view.findViewById<ImageView>(com.rahulsaini.alertx.R.id.toast_img)

        // CardBackgroundColor use karein taaki radius maintain rahe
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
            } else {
                isVisible = false
            }
        }
    }

    private fun createView(activity: Activity, rootView: ViewGroup): View {
        return LayoutInflater.from(activity).inflate(com.rahulsaini.alertx.R.layout.toast_message, rootView, false)
    }

    private fun addToScreen(view: View, rootView: FrameLayout){
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.BOTTOM
        params.setMargins(40, 0, 40, 100) // Space from sides and bottom
        rootView.addView(view, params)
    }

    private fun scheduleAutoDismiss(view: View) {
        autoDismissHandler = HandlerCompat.createAsync(Looper.getMainLooper())
        autoDismissRunnable = Runnable {
            if (view.parent != null) {
                animateDown(view) {
                    dismissView(view)
                }
            }
        }
        autoDismissHandler?.postDelayed(autoDismissRunnable!!, style.duration)
    }

    private fun dismissView(view: View){
        (view.parent as? ViewGroup)?.removeView(view)
    }

    /**
     * Modern property animation: Visuals and Touch Area move together.
     */
    fun animationUp(view: View, onCompleted: () -> Unit){
        // Start from below and transparent
        view.translationY = 500f
        view.alpha = 0f
        
        view.animate()
            .translationY(0f) // Move to layout position
            .alpha(1f)
            .setDuration(500)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction { onCompleted() }
            .start()
            
        // REMOVED: AnimationUtils (Legacy) block that was breaking touch events
    }

    fun animateDown(view: View, onCompleted: ()-> Unit){
        view.animate()
            .translationY(300f) // Move back down
            .alpha(0f)
            .setDuration(300)
            .withEndAction { onCompleted() }
            .start()
    }
}
