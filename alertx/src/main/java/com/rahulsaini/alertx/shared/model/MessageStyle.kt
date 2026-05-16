package com.rahulsaini.alertx.shared.model

import android.graphics.Color
import androidx.annotation.ColorRes
import com.rahulsaini.alertx.R

data class MessageStyle(
    @ColorRes val containerBackgroundColorRes: Int = R.color.alertx_info,
    val textColor: Int = Color.WHITE,
    val duration: Long = 2000,
    val showIcon: Boolean = true,
    val iconResource: Int? = null,
    val iconTint: Int = Color.WHITE,
    val animationType: AlertAnimationType = AlertAnimationType.SLIDE_FROM_BOTTOM
)
