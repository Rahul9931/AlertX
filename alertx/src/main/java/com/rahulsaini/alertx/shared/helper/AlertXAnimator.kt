package com.rahulsaini.alertx.shared.helper

import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
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
            .setInterpolator(android.view.animation.OvershootInterpolator())
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

    fun View.slideIn(fromDirection: Direction, useDebounce: Boolean, duration: Long = 500, onCompleted: () -> Unit){

        when(fromDirection){
            Direction.LEFT -> this.translationX = -this.getScreenWidth()
            Direction.RIGHT -> this.translationX = this.getScreenWidth()
            Direction.BOTTOM -> this.translationY = 500f
        }
        val interplator = if (useDebounce) OvershootInterpolator(1.2f) else DecelerateInterpolator()
        this.alpha = 0f
        this.animate()
            .translationX(0f)
            .translationY(0f) // Move to layout position
            .alpha(1f)
            .setDuration(duration)
            .setInterpolator(interplator)
            .withEndAction { onCompleted() }
            .start()
    }
    fun View.slideOut(fromDirection: Direction, useDebounce: Boolean, duration: Long = 300, onCompleted: () -> Unit){
        val animator = this.animate()
            .alpha(0f)
            .setDuration(duration)
            .withEndAction { onCompleted() }
        when(fromDirection){
            Direction.LEFT -> {
                animator.translationX(-this.getScreenWidth())
            }
            Direction.RIGHT -> {
                animator.translationX(this.getScreenWidth())
            }
            Direction.BOTTOM -> {
                animator.translationY(300f)
            }
        }
        animator.start()
    }
}