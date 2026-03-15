package com.rahulsaini.alertx.topDialog

import android.R
import android.app.Activity
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
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

object AlertXTop {

    data class MessageStyle(
        @ColorRes val containerBackgroundColor: Int = com.rahulsaini.alertx.R.color.info,
        val textColor: Int = Color.WHITE,
        val duration: Long = 2000,
        val showIcon: Boolean = true,
        val iconResource: Int? = null,
        val iconTint: Int = Color.WHITE
    )

    class GlobalConfig{
        var successStyle: MessageStyle = MessageStyle(
            containerBackgroundColor = com.rahulsaini.alertx.R.color.success
        )
        var infoStyle: MessageStyle = MessageStyle(
            containerBackgroundColor = com.rahulsaini.alertx.R.color.info
        )
        var errorStyle: MessageStyle = MessageStyle(
            containerBackgroundColor = com.rahulsaini.alertx.R.color.error
        )
    }

    private val globalConfig = GlobalConfig()

    private fun getGlobalConfigStyle() = globalConfig

    val alertQueue: ArrayDeque<TopAlertMessage> = ArrayDeque()
    var isShowing: Boolean = false

    fun initialize(activity: Activity, config: GlobalConfig.()-> Unit){
        globalConfig.apply(config)
    }

    fun enqueue(alert: TopAlertMessage){
        alertQueue.add(alert)
        if (!isShowing){
            nextShow()
        }
    }

    private fun nextShow() {
        val next = alertQueue.removeFirstOrNull() ?: return
        isShowing = true
        next.showInterval {
            isShowing = false
            nextShow()
        }
    }

    class Builder(private val activity: Activity){
        private var message: String = ""
        private var customStyle: MessageStyle? = null
        private var type = MessageType.INFO

        fun setMessage(message:String) = apply{
            this.message = message
        }

        fun setSuccess() = apply {
            this.type = MessageType.SUCCESS
        }

        fun setInfo() = apply {
            this.type = MessageType.INFO
        }

        fun setError() = apply {
            this.type = MessageType.ERROR
        }

        fun show(){
            val finalStyle = customStyle ?: getStyleFromType()
            val finalIcon = finalStyle.iconResource ?: getDefaultIconForType()
            val styleWithIcon = finalStyle.copy(
                iconResource = finalIcon
            )

            enqueue(TopAlertMessage(activity, message, styleWithIcon))
        }

        fun getStyleFromType(): MessageStyle{
            return when(type){
                MessageType.SUCCESS -> globalConfig.successStyle
                MessageType.INFO -> globalConfig.infoStyle
                MessageType.ERROR -> globalConfig.errorStyle
            }
        }

        fun getDefaultIconForType(): Int{
            return when(type){
                MessageType.SUCCESS -> com.rahulsaini.alertx.R.drawable.check_circle_24
                MessageType.INFO -> com.rahulsaini.alertx.R.drawable.info_24
                MessageType.ERROR -> com.rahulsaini.alertx.R.drawable.error_24
            }
        }

    }

    private enum class MessageType{
        SUCCESS, INFO, ERROR
    }

    class TopAlertMessage(
        private val activity: Activity,
        private var message: String,
        private var style: MessageStyle
    ){

        var onDismiss: (()-> Unit)? = null

        fun showInterval(onDismiss: ()-> Unit){
            this.onDismiss = onDismiss
            show()
        }

        fun dismissView(){
            onDismiss?.invoke()
        }

        fun show(){
            // 1. Get the root layout of the activity (android.R.id.content)
            val rootView = activity.window.decorView.findViewById<FrameLayout>(R.id.content)

            // 2. Create the view with parent context to respect XML layout attributes
            var view = createView(activity, rootView)

            // 3. Apply text and style
            applyStyle(activity,view)

            // 4. Animate and add to screen
            animateIn(view)
            addToScreen(view, rootView)

            // 5. Auto-dismiss after duration
            scheduleViewDismiss(view)
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
                ContextCompat.getColor(activity, style.containerBackgroundColor)
            )
            iconRes.apply {
                isVisible = style.showIcon
                setImageResource(style.iconResource ?: 1)
                setColorFilter(style.iconTint)
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

        private fun scheduleViewDismiss(view: View){
            Handler(Looper.getMainLooper()).postDelayed({
                val slideUp = try {
                    AnimationUtils.loadAnimation(activity, com.rahulsaini.alertx.R.anim.slide_up)
                } catch (e: Exception) {
                    null
                }

                if (slideUp != null) {
                    view.startAnimation(slideUp)
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    (view.parent as? ViewGroup)?.removeView(view)
                    dismissView()
                }, 300)
            }, style.duration)
        }
    }

// Quick Methods
    fun showSuccess(activity: Activity, message: String){
    Builder(activity)
        .setMessage(message)
        .setSuccess()
        .show()
    }

    fun showInfo(activity: Activity, message: String){
        Builder(activity)
            .setMessage(message)
            .setInfo()
            .show()
    }

    fun showError(activity: Activity, message: String){
        Builder(activity)
            .setMessage(message)
            .setError()
            .show()
    }

//    custom message for full control of the style
fun showCustomMessage(activity: Activity, message: String, style: MessageStyle){
    TopAlertMessage(activity,message, style).show()
}
}