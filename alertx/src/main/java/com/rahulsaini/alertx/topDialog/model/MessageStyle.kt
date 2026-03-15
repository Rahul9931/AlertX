package com.rahulsaini.alertx.topDialog.model

import android.graphics.Color
import androidx.annotation.ColorRes

data class MessageStyle(
    @ColorRes val containerBackgroundColor: Int = com.rahulsaini.alertx.R.color.info,
    val textColor: Int = Color.WHITE,
    val duration: Long = 2000,
    val showIcon: Boolean = true,
    val iconResource: Int? = null,
    val iconTint: Int = Color.WHITE
)
