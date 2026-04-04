package com.rahulsaini.alertx.alertXTop.config

import com.rahulsaini.alertx.R
import com.rahulsaini.alertx.alertXTop.model.MessageStyle

class GlobalConfig{
    var successStyle: MessageStyle = MessageStyle(
        containerBackgroundColorRes = R.color.success
    )
    var infoStyle: MessageStyle = MessageStyle(
        containerBackgroundColorRes = R.color.info
    )
    var errorStyle: MessageStyle = MessageStyle(
        containerBackgroundColorRes = R.color.error
    )
}