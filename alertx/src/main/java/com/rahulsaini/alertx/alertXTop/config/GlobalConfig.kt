package com.rahulsaini.alertx.alertXTop.config

import com.rahulsaini.alertx.R
import com.rahulsaini.alertx.shared.model.MessageStyle

class GlobalConfig{
    var successStyle: MessageStyle = MessageStyle(
        containerBackgroundColorRes = R.color.alertx_success
    )
    var infoStyle: MessageStyle = MessageStyle(
        containerBackgroundColorRes = R.color.alertx_info
    )
    var errorStyle: MessageStyle = MessageStyle(
        containerBackgroundColorRes = R.color.alertx_error
    )
}
