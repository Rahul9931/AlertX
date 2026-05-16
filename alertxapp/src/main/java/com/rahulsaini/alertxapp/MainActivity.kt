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
import com.rahulsaini.alertx.shared.model.AlertAnimationType
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
            AlertXToast.showSuccessToast(this, "this is my success toast message")
                .setAnimation(AlertAnimationType.FADE)
                .show()

        }

        btnInfo.setOnClickListener {
            AlertXToast.showInfoToast(this, "this is my info toast message, this is my info toast message")
                .setAnimation(AlertAnimationType.SLIDE_FROM_LEFT)
                .show()
        }

        btnError.setOnClickListener {
            AlertXToast.showErrorToast(this, "this is my error toast message, this is my error toast message, this is my error toast message, this is my error toast message")
                .setAnimation(AlertAnimationType.SLIDE_FROM_RIGHT)
                .show()
        }

        btnCustom.setOnClickListener {
            var customStyle = MessageStyle(
                containerBackgroundColorRes = R.color.info,
                textColor = Color.CYAN,
                duration = 5000,
                showIcon = true,
                iconResource = R.drawable.ic_android_black_24,
                iconTint = Color.CYAN
            )
            AlertXToast.showCustomMessage(this, "This is my custom message with custom style", customStyle)
        }

    }
}