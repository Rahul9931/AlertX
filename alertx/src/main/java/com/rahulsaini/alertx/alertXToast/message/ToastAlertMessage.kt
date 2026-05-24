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
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.HandlerCompat
import androidx.core.view.isVisible
import androidx.core.view.setMargins
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.card.MaterialCardView
import com.rahulsaini.alertx.shared.helper.AlertXAnimator.fadeIn
import com.rahulsaini.alertx.shared.helper.AlertXAnimator.fadeOut
import com.rahulsaini.alertx.shared.helper.AlertXAnimator.morphIn
import com.rahulsaini.alertx.shared.helper.AlertXAnimator.morphOut
import com.rahulsaini.alertx.shared.helper.AlertXAnimator.slideIn
import com.rahulsaini.alertx.shared.helper.AlertXAnimator.slideOut
import com.rahulsaini.alertx.shared.helper.AlertXAnimator.zoomIn
import com.rahulsaini.alertx.shared.helper.AlertXAnimator.zoomOut
import com.rahulsaini.alertx.shared.model.AlertAnimationType
import com.rahulsaini.alertx.shared.model.AlertPosition
import com.rahulsaini.alertx.shared.model.Direction
import kotlinx.coroutines.Runnable
import java.lang.ref.WeakReference

class ToastAlertMessage(
    private val activity: Activity,
    private val message: String,
    private val style: MessageStyle
) {

    private var autoDismissHandler: Handler? = null
    private var autoDismissRunnable: Runnable? = null
    private var onDismiss: (() -> Unit)? = null
    private var activityRef = WeakReference(activity)

    fun showWithCallback(onDismiss: () -> Unit) {
        this.onDismiss = onDismiss
        show()
    }

    fun show() {

        var activity = activityRef.get()

        if ( activity == null || activity.isFinishing || activity.isDestroyed){
            onDismiss?.invoke()
            return
        }

        if (activity is LifecycleOwner){
            if (!activity.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)){
                activity.lifecycle.addObserver(object : LifecycleObserver{
                    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                    fun resume(){
                        show()
                        activity.lifecycle.removeObserver(this)
                    }
                })
                return
            }
        }

        val rootView = try {
            activity.window.decorView.findViewById<FrameLayout>(R.id.content)
        } catch (e: Exception) {
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
            animateDown(view) {
                dismissView(view)
            }
        }
    }

    private fun applyStyle(activity: Activity, view: View) {
        val toastContainer =
            view.findViewById<MaterialCardView>(com.rahulsaini.alertx.R.id.toast_msg_container)
        val toastMsg = view.findViewById<TextView>(com.rahulsaini.alertx.R.id.toast_txt)
        val toastImg = view.findViewById<ImageView>(com.rahulsaini.alertx.R.id.toast_img)

        toastContainer.setCardBackgroundColor(
            ContextCompat.getColor(activity, style.containerBackgroundColorRes)
        )
        toastMsg.text = message
        toastMsg.setTextColor(style.textColor)

        toastImg.apply {
            if (style.showIcon && style.iconResource != null) {
                isVisible = true
                setImageResource(style.iconResource)
                setColorFilter(style.iconTint)
            } else {
                isVisible = false
            }
        }
    }

    private fun createView(activity: Activity, rootView: ViewGroup): View {
        return LayoutInflater.from(activity)
            .inflate(com.rahulsaini.alertx.R.layout.toast_message, rootView, false)
    }

    private fun addToScreen(view: View, rootView: FrameLayout) {
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        // Set gravity to CENTER_HORIZONTAL so MORPH_FROM_BALL starts in the middle
        if (style.position == AlertPosition.TOP){
            params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            params.setMargins(20, 100, 20, 0)
        }
        else{
            params.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            params.setMargins(20, 0, 20, 100)
        }
        view.elevation = 100f
        rootView.addView(view, params)
        view.bringToFront()
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

    private fun dismissView(view: View) {
        (view.parent as? ViewGroup)?.removeView(view)
        onDismiss?.invoke()
    }

    fun animationUp(view: View, onCompleted: () -> Unit) {
        val verticalDir = if (style.position == AlertPosition.TOP) Direction.TOP else Direction.BOTTOM
        when (style.animationType) {
            AlertAnimationType.SLIDE_FROM_VERTICAL -> {
                view.slideIn(verticalDir, false) { onCompleted() }
            }
            AlertAnimationType.SLIDE_FROM_VERTICAL_BOUNCE -> {
                view.slideIn(verticalDir, true) { onCompleted() }
            }
            AlertAnimationType.SLIDE_FROM_LEFT -> {
                view.slideIn(Direction.LEFT, false) { onCompleted() }
            }
            AlertAnimationType.SLIDE_FROM_LEFT_BOUNCE -> {
                view.slideIn(Direction.LEFT, true) { onCompleted() }
            }
            AlertAnimationType.SLIDE_FROM_RIGHT -> {
                view.slideIn(Direction.RIGHT, false) { onCompleted() }
            }
            AlertAnimationType.SLIDE_FROM_RIGHT_BOUNCE -> {
                view.slideIn(Direction.RIGHT, true) { onCompleted() }
            }
            AlertAnimationType.ZOOM -> {
                view.zoomIn { onCompleted() }
            }
            AlertAnimationType.FADE -> {
                view.fadeIn { onCompleted() }
            }
            AlertAnimationType.MORPH_FROM_BALL -> {
                view.morphIn(position = style.position) { onCompleted() }
            }
        }
    }

    fun animateDown(view: View, onCompleted: () -> Unit) {
        val verticalDir = if (style.position == AlertPosition.TOP) Direction.TOP else Direction.BOTTOM
        when (style.animationType) {
            AlertAnimationType.SLIDE_FROM_VERTICAL -> {
                view.slideOut(verticalDir, false) { onCompleted() }
            }
            AlertAnimationType.SLIDE_FROM_VERTICAL_BOUNCE -> {
                view.slideOut(verticalDir, true) { onCompleted() }
            }
            AlertAnimationType.SLIDE_FROM_LEFT -> {
                view.slideOut(Direction.LEFT, false) { onCompleted() }
            }
            AlertAnimationType.SLIDE_FROM_LEFT_BOUNCE -> {
                view.slideOut(Direction.LEFT, true) { onCompleted() }
            }
            AlertAnimationType.SLIDE_FROM_RIGHT -> {
                view.slideOut(Direction.RIGHT, false) { onCompleted() }
            }
            AlertAnimationType.SLIDE_FROM_RIGHT_BOUNCE -> {
                view.slideOut(Direction.RIGHT, true) { onCompleted() }
            }
            AlertAnimationType.ZOOM -> {
                view.zoomOut { onCompleted() }
            }
            AlertAnimationType.FADE -> {
                view.fadeOut { onCompleted() }
            }
            AlertAnimationType.MORPH_FROM_BALL -> {
                view.morphOut(position = style.position) { onCompleted() }
            }
        }
    }
}
