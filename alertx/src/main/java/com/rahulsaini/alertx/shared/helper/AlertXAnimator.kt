package com.rahulsaini.alertx.shared.helper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.rahulsaini.alertx.shared.model.Direction

internal object AlertXAnimator {

    private fun View.getScreenWidth() = resources.displayMetrics.widthPixels.toFloat()

    fun View.zoomIn(duration: Long = 800, onCompleted: () -> Unit){
        this.scaleX = 0f
        this.scaleY = 0f
        this.alpha = 0f
        this.animate()
            .scaleX(1f)
            .scaleY(1f)
            .alpha(1f)
            .setDuration(duration)
            .setInterpolator(OvershootInterpolator())
            .withEndAction { onCompleted() }
            .start()
    }

    fun View.zoomOut(duration: Long = 800, onCompleted: () -> Unit){
        this.animate()
            .scaleX(0f)
            .scaleY(0f)
            .alpha(0f)
            .setDuration(duration)
            .withEndAction { onCompleted() }
            .start()
    }

    fun View.fadeIn(duration: Long = 500, onCompleted: () -> Unit){
        this.alpha = 0f
        this.animate()
            .alpha(1f)
            .setDuration(duration)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction { onCompleted() }
            .start()
    }

    fun View.fadeOut(duration: Long = 400, onCompleted: () -> Unit){
        this.animate()
            .alpha(0f)
            .setDuration(duration)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction { onCompleted() }
            .start()
    }

    fun View.slideIn(fromDirection: Direction, useDebounce: Boolean, duration: Long = 1200, onCompleted: () -> Unit){
        when(fromDirection){
            Direction.LEFT -> this.translationX = -this.getScreenWidth()
            Direction.RIGHT -> this.translationX = this.getScreenWidth()
            Direction.BOTTOM -> this.translationY = 600f
        }
        // Using standard Android OvershootInterpolator as requested
        val interpolator = if (useDebounce) OvershootInterpolator(1.2f) else DecelerateInterpolator()
        
        this.alpha = 0f
        this.animate()
            .translationX(0f)
            .translationY(0f) 
            .alpha(1f)
            .setDuration(duration)
            .setInterpolator(interpolator)
            .withEndAction { onCompleted() }
            .start()
    }
    
    fun View.slideOut(fromDirection: Direction, useDebounce: Boolean, duration: Long = 300, onCompleted: () -> Unit){
        val animator = this.animate()
            .alpha(0f)
            .setDuration(duration)
            .withEndAction { onCompleted() }
        when(fromDirection){
            Direction.LEFT -> animator.translationX(-this.getScreenWidth())
            Direction.RIGHT -> animator.translationX(this.getScreenWidth())
            Direction.BOTTOM -> animator.translationY(600f)
        }
        animator.start()
    }

    /**
     * Morph In: Rises as a ball and expands into a toast.
     * Fixed circle shape issues by overriding minHeight and managing text visibility.
     */
    fun View.morphIn(duration: Long = 1000, onCompleted: () -> Unit) {
        val cardView = this as? MaterialCardView ?: return
        val textView = findViewById<TextView>(com.rahulsaini.alertx.R.id.toast_txt)
        val iconView = findViewById<ImageView>(com.rahulsaini.alertx.R.id.toast_img)

        this.alpha = 0f 

        this.post {
            val targetWidth = this.width
            val targetHeight = this.height
            val targetRadius = cardView.radius
            
            val density = resources.displayMetrics.density
            val ballSize = (64 * density).toInt() // Diameter of the ball

            val params = this.layoutParams
            params.width = ballSize
            params.height = ballSize
            cardView.minimumHeight = 0 // Override XML minHeight for perfect circle
            this.layoutParams = params
            cardView.radius = ballSize / 2f
            
            // Hide text to prevent vertical stretching
            textView?.visibility = View.GONE
            
            iconView?.let { iv ->
                // Center icon inside the ball
                val ballCenter = ballSize / 2f
                iv.translationX = ballCenter - (iv.width / 2f) - iv.left
                iv.translationY = ballCenter - (iv.height / 2f) - iv.top
            }

            this.translationY = 600f
            this.alpha = 1f

            // 1. Rise up phase
            this.animate()
                .translationY(0f)
                .setDuration(400)
                .setInterpolator(DecelerateInterpolator())
                .withEndAction {
                    // 2. Expand phase (Morph)
                    val morphAnimator = ValueAnimator.ofFloat(0f, 1f)
                    morphAnimator.addUpdateListener { animator ->
                        val f = animator.animatedValue as Float
                        
                        // Expand dimensions
                        params.width = (ballSize + (targetWidth - ballSize) * f).toInt()
                        params.height = (ballSize + (targetHeight - ballSize) * f).toInt()
                        this.layoutParams = params
                        
                        // Morph radius from circle to target corner radius
                        cardView.radius = (ballSize / 2f) + (targetRadius - (ballSize / 2f)) * f
                        
                        iconView?.let { iv ->
                            // Move icon back to its original layout position
                            val startX = (ballSize / 2f) - (iv.width / 2f) - iv.left
                            val startY = (ballSize / 2f) - (iv.height / 2f) - iv.top
                            iv.translationX = startX * (1 - f)
                            iv.translationY = startY * (1 - f)
                        }
                    }
                    
                    // Show text during expansion
                    textView?.visibility = View.VISIBLE
                    textView?.alpha = 0f
                    textView?.animate()?.alpha(1f)?.setDuration(400)?.start()

                    morphAnimator.duration = 500
                    morphAnimator.interpolator = OvershootInterpolator(0.8f)
                    morphAnimator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            // Reset to final layout parameters
                            params.width = targetWidth
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            this@morphIn.layoutParams = params
                            onCompleted()
                        }
                    })
                    morphAnimator.start()
                }
                .start()
        }
    }

    /**
     * Morph Out: Shrinks back to a ball and slides down.
     */
    fun View.morphOut(duration: Long = 500, onCompleted: () -> Unit) {
        val cardView = this as? MaterialCardView ?: return
        val textView = findViewById<TextView>(com.rahulsaini.alertx.R.id.toast_txt)
        val iconView = findViewById<ImageView>(com.rahulsaini.alertx.R.id.toast_img)
        
        val startWidth = this.width
        val startHeight = this.height
        val startRadius = cardView.radius
        
        val density = resources.displayMetrics.density
        val ballSize = (64 * density).toInt()
        val params = this.layoutParams

        textView?.animate()?.alpha(0f)?.setDuration(200)?.withEndAction {
            textView?.visibility = View.GONE
        }?.start()

        val morphAnimator = ValueAnimator.ofFloat(0f, 1f)
        morphAnimator.addUpdateListener { animator ->
            val f = animator.animatedValue as Float
            
            // Shrink dimensions
            params.width = (startWidth - (startWidth - ballSize) * f).toInt()
            params.height = (startHeight - (startHeight - ballSize) * f).toInt()
            this.layoutParams = params
            
            // Revert radius to circle
            cardView.radius = startRadius + ((ballSize / 2f) - startRadius) * f
            
            iconView?.let { iv ->
                // Center icon in the shrinking ball
                val endX = (ballSize / 2f) - (iv.width / 2f) - iv.left
                val endY = (ballSize / 2f) - (iv.height / 2f) - iv.top
                iv.translationX = endX * f
                iv.translationY = endY * f
            }
        }
        morphAnimator.duration = 400
        morphAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // Slide ball down out of screen
                this@morphOut.animate()
                    .translationY(600f)
                    .alpha(0f)
                    .setDuration(300)
                    .withEndAction { onCompleted() }
                    .start()
            }
        })
        morphAnimator.start()
    }
}
