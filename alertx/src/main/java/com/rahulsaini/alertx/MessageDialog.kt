package com.rahulsaini.alertx

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible

public class MessageDialog private constructor(
    private val context: Context,
    private val message: String,
    private val type: MessageType,
    private val duration: Long,
    private val config: MessageConfig
) {

    public enum class MessageType {
        SUCCESS, INFO, ERROR
    }

    public data class MessageConfig(
        public val backgroundColor: Int = Color.WHITE,
        public val textColor: Int = Color.BLACK,
        public val iconTint: Int = Color.BLACK,
        public val cornerRadius: Float = 8f,
        public val elevation: Float = 8f,
        public val animationDuration: Long = 300,
        public val showIcon: Boolean = true,
        public val iconResource: Int? = null
    ) {
        public companion object {
            public val defaultSuccess: MessageConfig = MessageConfig(
                backgroundColor = Color.parseColor("#4CAF50"),
                textColor = Color.WHITE,
                iconTint = Color.WHITE
            )

            public val defaultInfo: MessageConfig = MessageConfig(
                backgroundColor = Color.parseColor("#2196F3"),
                textColor = Color.WHITE,
                iconTint = Color.WHITE
            )

            public val defaultError: MessageConfig = MessageConfig(
                backgroundColor = Color.parseColor("#F44336"),
                textColor = Color.WHITE,
                iconTint = Color.WHITE
            )
        }
    }

    private var rootView: ViewGroup? = null
    private var messageView: View? = null

    public fun show() {
        if (rootView == null) {
            setupViews()
        }

        val parent = findRootView() ?: return
        parent.addView(messageView)

        // Animate slide down
        animateIn()

        // Auto dismiss after duration
        Handler(Looper.getMainLooper()).postDelayed({
            dismiss()
        }, duration)
    }

    fun dismiss() {
        animateOut {
            messageView?.let { view ->
                (view.parent as? ViewGroup)?.removeView(view)
            }
        }
    }

    public fun setupViews() {
        val inflater = LayoutInflater.from(context)
        messageView = inflater.inflate(R.layout.layout_top_message, null)

        val container = messageView?.findViewById<FrameLayout>(R.id.message_container)
        val tvMessage = messageView?.findViewById<TextView>(R.id.tv_message)
        val ivIcon = messageView?.findViewById<ImageView>(R.id.iv_icon)

        // Apply configuration
        container?.let {
            val background = GradientDrawable()
            background.cornerRadius = config.cornerRadius
            background.setColor(config.backgroundColor)
            it.background = background
            it.elevation = config.elevation

            val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = getStatusBarHeight()
                setMargins(16, 16, 16, 0)
            }
            it.layoutParams = params
        }

        tvMessage?.apply {
            text = message
            setTextColor(config.textColor)
        }

        ivIcon?.apply {
            isVisible = config.showIcon
            val iconRes = config.iconResource ?: getDefaultIconForType(type)
            setImageResource(iconRes)
            setColorFilter(config.iconTint)
        }
    }

    public fun getDefaultIconForType(type: MessageType): Int {
        return when (type) {
            MessageType.SUCCESS -> R.drawable.check_circle_24
            MessageType.INFO -> R.drawable.error_24
            MessageType.ERROR -> R.drawable.error_24
        }
    }

    public fun animateIn() {
        messageView?.let { view ->
            view.translationY = -view.height.toFloat()
            ObjectAnimator.ofFloat(view, "translationY", 0f)
                .setDuration(config.animationDuration)
                .start()
        }
    }

    private fun animateOut(onComplete: () -> Unit) {
        messageView?.let { view ->
            ObjectAnimator.ofFloat(view, "translationY", -view.height.toFloat()).apply {
                duration = config.animationDuration
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        onComplete()
                    }
                })
                start()
            }
        }
    }

    private fun findRootView(): ViewGroup? {
        val activity = context as? android.app.Activity
        return activity?.window?.decorView?.findViewById(android.R.id.content) as? ViewGroup
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    class Builder(private val context: Context) {
        private var message: String = ""
        private var type: MessageType = MessageType.INFO
        private var duration: Long = 3000
        private var config: MessageConfig = MessageConfig.defaultInfo

        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun setType(type: MessageType): Builder {
            this.type = type
            when (type) {
                MessageType.SUCCESS -> config = MessageConfig.defaultSuccess
                MessageType.INFO -> config = MessageConfig.defaultInfo
                MessageType.ERROR -> MessageConfig.defaultError
            }
            return this
        }

        fun setDuration(duration: Long): Builder {
            this.duration = duration
            return this
        }

        fun setConfig(config: MessageConfig): Builder {
            this.config = config
            return this
        }

        fun build(): MessageDialog {
            return MessageDialog(context, message, type, duration, config)
        }

        fun show(): MessageDialog {
            val dialog = build()
            dialog.show()
            return dialog
        }
    }
}