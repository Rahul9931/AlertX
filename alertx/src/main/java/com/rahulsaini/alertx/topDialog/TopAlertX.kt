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
import android.widget.TextView

object TopAlertX {
    data class MessageStyle(
        val backgroundColor: Int = Color.WHITE,
        val textColor: Int = Color.WHITE,
        val duration: Long = 2000
    )

    class GlobalConfig{
        val successStyle: MessageStyle = MessageStyle(
            backgroundColor = Color.GREEN
        )
        val infoStyle: MessageStyle = MessageStyle(
            backgroundColor = Color.YELLOW
        )
        val errorStyle: MessageStyle = MessageStyle(
            backgroundColor = Color.RED
        )
    }

    private val globalConfig = GlobalConfig()

    private fun getGlobalConfigStyle() = globalConfig

    fun initialize(activity: Activity, config: GlobalConfig.()-> Unit){
        globalConfig.apply(config)
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
            topAlertMessage(activity, message, finalStyle).show()
        }

        fun getStyleFromType(): MessageStyle{
            return when(type){
                MessageType.SUCCESS -> globalConfig.successStyle
                MessageType.INFO -> globalConfig.infoStyle
                MessageType.ERROR -> globalConfig.errorStyle
            }
        }

    }

    private enum class MessageType{
        SUCCESS, INFO, ERROR
    }

    class topAlertMessage(
        private val activity: Activity,
        private var message: String,
        private var style: MessageStyle
    ){

        fun show(){
            // 1. Get the root layout of the activity (android.R.id.content)
            val rootView = activity.window.decorView.findViewById<FrameLayout>(R.id.content)

            // 2. Create the view with parent context to respect XML layout attributes
            var view = createView(activity, rootView)

            // 3. Apply text and style
            applyStyle(view)

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

        private fun applyStyle(view: View) {
            val txt_message = view.findViewById<TextView>(com.rahulsaini.alertx.R.id.txt_message)
            txt_message.text = message
            txt_message.setBackgroundColor(style.backgroundColor)
            // Note: Padding is handled in message.xml to push text down from the top
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
    topAlertMessage(activity,message, style).show()
}
}