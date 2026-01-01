package com.rahulsaini.alertx

import android.content.Context
import android.widget.Toast

object AlertXToast {

    fun show(
        context: Context,
        message: String,
        duration: Int = Toast.LENGTH_SHORT
    ){
        Toast.makeText(context.applicationContext, message, duration).show()
    }

}