package com.rahulsaini.alertxapp

import android.app.Application
import com.rahulsaini.alertx.alertXToast.AlertXToast
import com.rahulsaini.alertx.shared.model.MessageStyle

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

//        AlertXToast.initialize(this@MyApplication, {
//            successStyle = MessageStyle(
//                containerBackgroundColorRes = R.color.error,
//                iconResource = R.drawable.ic_android_black_24
//            )
//            infoStyle = MessageStyle(
//                containerBackgroundColorRes = R.color.success,
//                iconResource = R.drawable.ic_android_black_24
//            )
//            errorStyle = MessageStyle(
//                containerBackgroundColorRes = R.color.info,
//                iconResource = R.drawable.ic_android_black_24
//            )
//        })
    }
}