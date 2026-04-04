package com.rahulsaini.alertx.alertXTop.message

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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
import com.rahulsaini.alertx.alertXTop.model.MessageStyle
import kotlinx.coroutines.Runnable
import java.lang.ref.WeakReference
import kotlin.math.abs

class TopAlertMessage(
    private val activity: Activity,
    private var message: String,
    private var style: MessageStyle
){

    var onDismiss: (()-> Unit)? = null
    var isDismissed: Boolean = false
    private var autoDismissHandler: Handler? = null
    private var autoDismissRunnable: Runnable? = null
    private var currentView: View? = null

    private var activityRef = WeakReference(activity)

    fun showWithCallback(onDismiss: ()-> Unit){
        this.onDismiss = onDismiss
        show()
    }

    fun dismissView(view: View){
        (view.parent as? ViewGroup)?.removeView(view)
        onDismiss?.invoke()
        currentView?.let { view ->
            (view.parent as? ViewGroup)?.removeView(view)
            onDismiss?.invoke()
            currentView = null

        }
    }

    fun cancelAutoDismiss(){
        autoDismissHandler?.removeCallbacksAndMessages(null)
        autoDismissHandler = null
        autoDismissRunnable = null
    }

    fun forceDismiss(){
        if (!isDismissed){
            isDismissed = true
            cancelAutoDismiss()
        }
    }

    fun show(){
        val activity = activityRef.get() ?: return   // if null activity destroyed

        if (activity.isFinishing || activity.isDestroyed) return   // don't show if finishing
        // 1. Get the root layout of the activity (android.R.id.content)

        // lifecycle check
        if (activity is LifecycleOwner){
            // check if activity is resumed (visible and interactive)
            if (!activity.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)){
                // activity is in background - postpone showing
                activity.lifecycle.addObserver(object : LifecycleObserver{
                    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                    fun onResume(){
                        show() // try again when activity resume
                        activity.lifecycle.removeObserver(this)
                    }
                })
                return // don't show now
            }
        }

        val rootView = try {
            activity.window.decorView.findViewById<FrameLayout>(R.id.content)
        }
        catch (e: Exception){
            null
        } ?: return

        // 2. Create the view with parent context to respect XML layout attributes
        var view = createView(activity, rootView)
        currentView = view
        view.contentDescription = "alert $message"
        view.isFocusable = true
        view.isFocusableInTouchMode = true

        // 3. Apply text and style
        applyStyle(activity,view)

        attachSwipeToDismiss(view)

        // 4. Animate and add to screen
        animateIn(view)
        addToScreen(view, rootView)

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
        val messageContainer = view.findViewById<ConstraintLayout>(com.rahulsaini.alertx.R.id.message_container)
        val iconRes = view.findViewById<ImageView>(com.rahulsaini.alertx.R.id.img_message)
        txt_message.text = message
        txt_message.setTextColor(style.textColor)
        messageContainer.setBackgroundColor(
            ContextCompat.getColor(activity, style.containerBackgroundColorRes)
        )
        iconRes.apply {
            if (style.showIcon && style.iconResource != null){
                isVisible = true
                setImageResource(style.iconResource!!)
                setColorFilter(style.iconTint)
                contentDescription = "Alert Icon"
            }
            else{
                isVisible = false
            }
        }
    }

    private fun animateIn(view: View){
        view.startAnimation(AnimationUtils.loadAnimation(activity, com.rahulsaini.alertx.R.anim.slide_down))
    }

    private fun addToScreen(view: View, rootView: FrameLayout){
        // Use FrameLayout.LayoutParams to control positioning
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        // Align to top of the screen
        params.gravity = Gravity.TOP

        // If you want additional space from the very top edge of the screen:
        // params.topMargin = 10 // Optional: adds extra margin if needed

        rootView.addView(view, params)
    }

    private fun scheduleAutoDismiss(view: View){
        val slideUp = try {
            AnimationUtils.loadAnimation(activity, com.rahulsaini.alertx.R.anim.slide_up)
        } catch (e: Exception) {
            null
        }

        autoDismissHandler = HandlerCompat.createAsync(Looper.getMainLooper())
        autoDismissRunnable = Runnable {
            if (isDismissed) return@Runnable

            if (slideUp != null) {
                view.startAnimation(slideUp)
                slideUp.setAnimationListener(object : Animation.AnimationListener{
                    override fun onAnimationEnd(animation: Animation?) {
                        if (!isDismissed) dismissView(view)
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}

                    override fun onAnimationStart(animation: Animation?) {}
                })
            }
        }
        autoDismissHandler?.postDelayed(autoDismissRunnable!!, style.duration)
    }

    fun attachSwipeToDismiss(view: View){
        var startX = 0f
        var startY = 0f
        view.setOnTouchListener { v,event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    startX = event.rawX
                    startY = event.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    var diffX = event.rawX - startX
                    var diffY = event.rawY - startY
                    if(abs(diffX) > abs(diffY)){
                        v.translationX = diffX
                    }
                    else{
                        v.translationY = diffY
                    }
                }

                MotionEvent.ACTION_UP -> {
                    Log.d("check_anim", "x value -> ${v.translationX}")
                    var swipedX = abs(v.translationX)
                    var swipedY = abs(v.translationY)

                    if (swipedX > 100){
                        dismissWithSwipe(view, isHorizontalSwipe = true,v)
                    }
                    else if (swipedY > 30){
                        dismissWithSwipe(view, v = v)
                    }
                    else{
                        v.animate().translationX(0f).start()
                        v.performClick()
                    }
                }
            }
            true
        }
    }

    fun dismissWithSwipe(view: View, isHorizontalSwipe: Boolean = false, v: View){
        if(isDismissed) return
        isDismissed = true

        if (isHorizontalSwipe){
            if (v.translationX < 0){
                view.animate()
                    .translationX(-view.width.toFloat())
                    .alpha(0f)
                    .setDuration(100)
                    .withEndAction {
                        dismissView(view)
                    }
            }
            else{
                view.animate()
                    .translationX(view.width.toFloat())
                    .alpha(0f)
                    .setDuration(100)
                    .withEndAction {
                        dismissView(view)
                    }
            }

        }
        else{
            view.animate()
                .translationY(-view.height.toFloat())
                .alpha(0f)
                .setDuration(100)
                .withEndAction {
                    dismissView(view)
                }
        }
    }
}