package com.rahulsaini.alertxapp

import android.app.Application

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

//        AlertXTop.initialize(this@MyApplication, {
//            successStyle = MessageStyle(
//                containerBackgroundColor = R.color.success,
//                iconResource = R.drawable.ic_android_black_24
//            )
//            infoStyle = MessageStyle(
//                containerBackgroundColor = R.color.info,
//                iconResource = R.drawable.ic_android_black_24
//            )
//            errorStyle = MessageStyle(
//                containerBackgroundColor = R.color.error,
//                iconResource = R.drawable.ic_android_black_24
//            )
//        })
    }
}