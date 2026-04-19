package com.rahulsaini.alertx.alertXToast

import com.rahulsaini.alertx.shared.config.GlobalConfig

object AlertXToast {
    private val globalConfig = GlobalConfig()

    internal fun getGlobalConfig() = globalConfig
}