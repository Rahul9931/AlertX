package com.rahulsaini.alertxapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rahulsaini.alertx.alertXToast.AlertXToast
import com.rahulsaini.alertx.alertXTop.AlertXTop
import com.rahulsaini.alertx.shared.model.MessageStyle

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var btnSuccess = findViewById<Button>(R.id.btn_success)
        var btnInfo = findViewById<Button>(R.id.btn_info)
        var btnError = findViewById<Button>(R.id.btn_error)
        var btnCustom = findViewById<Button>(R.id.btn_custom_msg)
        btnSuccess.setOnClickListener {
//            AlertXToast.show(this, "my custom toast")
//            AlertXTop.showSuccess(this, "success message sdnfoids iosndbfgiw oiewrfi dfiub fewui ds efiewoi dsf in weids wsegf iwenf popew dsaowe weifna")
            AlertXToast.showSuccessToast(this, "toast message successfull")
        }

        btnInfo.setOnClickListener {
//            var customStyle = MessageStyle(
//                containerBackgroundColorRes = R.color.black,
//                textColor = Color.RED,
//                iconResource = R.drawable.ic_android_black_24,
//                iconTint = Color.GRAY,
//                showIcon = true
//            )
            AlertXTop.showInfo(this, "this is my custom message")
        }

        btnError.setOnClickListener {
            var customStyle = MessageStyle(
                containerBackgroundColorRes = R.color.black,
                textColor = Color.RED,
                iconTint = Color.GRAY,
                showIcon = false
            )
            AlertXTop.showError(this, "this is my custom message")
        }

        btnCustom.setOnClickListener {
            var customStyle = MessageStyle(
                containerBackgroundColorRes = R.color.black,
                textColor = Color.RED,
                iconResource = R.drawable.ic_android_black_24,
                iconTint = Color.GRAY
            )
            AlertXTop.showCustomMessage(this, "this is my custom message", customStyle)
        }

    }
}