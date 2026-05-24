package com.rahulsaini.alertx.alertXSheet.message

import android.R
import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.HandlerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
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
import com.rahulsaini.alertx.shared.model.MessageStyle
import kotlinx.coroutines.Runnable
import java.lang.ref.WeakReference
import kotlin.math.abs

class SheetAlertMessage(
    private val activity: Activity,
    private var message: String,
    private var style: MessageStyle
) {

    var onDismiss: (() -> Unit)? = null
    var isDismissed: Boolean = false
    private var autoDismissHandler: Handler? = null
    private var autoDismissRunnable: Runnable? = null
    private var currentView: View? = null

    private var activityRef = WeakReference(activity)

    fun showWithCallback(onDismiss: () -> Unit) {
        this.onDismiss = onDismiss
        show()
    }

    fun dismissView(view: View) {
        (view.parent as? ViewGroup)?.removeView(view)
        onDismiss?.invoke()
        currentView?.let { view ->
            (view.parent as? ViewGroup)?.removeView(view)
            onDismiss?.invoke()
            currentView = null

        }
    }

    fun cancelAutoDismiss() {
        autoDismissHandler?.removeCallbacksAndMessages(null)
        autoDismissHandler = null
        autoDismissRunnable = null
    }

    fun forceDismiss() {
        if (!isDismissed) {
            isDismissed = true
            cancelAutoDismiss()
        }
    }

    fun show() {
        val activity = activityRef.get()   // if null activity destroyed

        if (activity == null || activity.isFinishing || activity.isDestroyed) {
            onDismiss?.invoke()
            return
        }

        // lifecycle check
        if (activity is LifecycleOwner) {
            // check if activity is resumed (visible and interactive)
            if (!activity.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                // activity is in background - postpone showing
                activity.lifecycle.addObserver(object : LifecycleObserver {
                    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                    fun onResume() {
                        show() // try again when activity resume
                        activity.lifecycle.removeObserver(this)
                    }
                })
                return // don't show now
            }
        }

        // 1. Get the root layout of the activity (android.R.id.content)
        val rootView = try {
            activity.window.decorView.findViewById<FrameLayout>(R.id.content)
        } catch (e: Exception) {
            null
        } ?: return

        // 2. Create the view with parent context to respect XML layout attributes
        var view = createView(activity, rootView)
        currentView = view
        view.contentDescription = "alert $message"
        view.isFocusable = true
        view.isFocusableInTouchMode = true

        // 3. Apply text and style
        applyStyle(activity, view)

        attachSwipeToDismiss(view)

        addToScreen(view, rootView)

        // 4. Animate and add to screen
        animationUp(view) {}

        // 5. Auto-dismiss after duration
        scheduleAutoDismiss(view)
    }

    private fun createView(activity: Activity, parent: ViewGroup): View {
        var inflater = LayoutInflater.from(activity)
        // Inflate with parent but don't attach yet to keep WRAP_CONTENT behavior
        return inflater.inflate(com.rahulsaini.alertx.R.layout.message, parent, false)
    }

    private fun applyStyle(activity: Activity, view: View) {
        val txt_message = view.findViewById<TextView>(com.rahulsaini.alertx.R.id.txt_message)
        val messageContainer =
            view.findViewById<ConstraintLayout>(com.rahulsaini.alertx.R.id.message_container)
        val iconRes = view.findViewById<ImageView>(com.rahulsaini.alertx.R.id.img_message)
        txt_message.text = message
        txt_message.setTextColor(style.textColor)
        messageContainer.setBackgroundColor(
            ContextCompat.getColor(activity, style.containerBackgroundColorRes)
        )
        iconRes.apply {
            if (style.showIcon && style.iconResource != null) {
                isVisible = true
                setImageResource(style.iconResource!!)
                setColorFilter(style.iconTint)
                contentDescription = "Alert Icon"
            } else {
                isVisible = false
            }
        }
    }

    fun animationUp(view: View, onCompleted: () -> Unit) {
        val verticalDir =
            if (style.position == AlertPosition.TOP) Direction.TOP else Direction.BOTTOM
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
        val verticalDir =
            if (style.position == AlertPosition.TOP) Direction.TOP else Direction.BOTTOM
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

    private fun addToScreen(view: View, rootView: FrameLayout) {
        // Use FrameLayout.LayoutParams to control positioning
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        // Align to top of the screen
        Log.d("check_position", "position -> ${style.position}")
        if (style.position == AlertPosition.TOP) {
            params.gravity = Gravity.TOP
        } else {
            params.gravity = Gravity.BOTTOM
            params.setMargins(0,0,0,80)
        }


        // If you want additional space from the very top edge of the screen:
        // params.topMargin = 10 // Optional: adds extra margin if needed

        rootView.addView(view, params)
    }

    private fun scheduleAutoDismiss(view: View) {

        autoDismissHandler = HandlerCompat.createAsync(Looper.getMainLooper())
        autoDismissRunnable = Runnable {
            if (isDismissed) return@Runnable

            animateDown(view) {
                if (!isDismissed) dismissView(view)
            }
        }
        autoDismissHandler?.postDelayed(autoDismissRunnable!!, style.duration)
    }

    fun attachSwipeToDismiss(view: View) {
        var startX = 0f
        var startY = 0f
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.rawX
                    startY = event.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    var diffX = event.rawX - startX
                    var diffY = event.rawY - startY
                    if (abs(diffX) > abs(diffY)) {
                        v.translationX = diffX
                    } else {
                        v.translationY = diffY
                    }
                }

                MotionEvent.ACTION_UP -> {
                    Log.d("check_anim", "x value -> ${v.translationX}")
                    var swipedX = abs(v.translationX)
                    var swipedY = abs(v.translationY)

                    if (swipedX > 100) {
                        dismissWithSwipe(view, isHorizontalSwipe = true, v)
                    } else if (swipedY > 30) {
                        dismissWithSwipe(view, v = v)
                    } else {
                        v.animate().translationX(0f).start()
                        v.performClick()
                    }
                }
            }
            true
        }
    }

    fun dismissWithSwipe(view: View, isHorizontalSwipe: Boolean = false, v: View) {
        if (isDismissed) return
        isDismissed = true

        val exitTranslationX =
            if (v.translationX < 0) -view.width.toFloat() else view.width.toFloat()
        val exitTranslationY = if (style.position == AlertPosition.TOP) -view.height.toFloat() else view.height.toFloat()

        if (isHorizontalSwipe) {
            view.animate()
                .translationX(exitTranslationX)
                .alpha(0f)
                .setDuration(100)
                .withEndAction {
                    dismissView(view)
                }
        } else {
            view.animate()
                .translationY(exitTranslationY)
                .alpha(0f)
                .setDuration(100)
                .withEndAction {
                    dismissView(view)
                }
        }
    }
}