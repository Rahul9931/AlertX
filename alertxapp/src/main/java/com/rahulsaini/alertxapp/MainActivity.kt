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
import com.rahulsaini.alertx.alertXSheet.AlertXSheet
import com.rahulsaini.alertx.shared.model.AlertAnimationType
import com.rahulsaini.alertx.shared.model.AlertPosition
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
        var btnWarn = findViewById<Button>(R.id.btn_warn)
        var btnError = findViewById<Button>(R.id.btn_error)
        var btnCustom = findViewById<Button>(R.id.btn_custom_msg)
        btnSuccess.setOnClickListener {
            AlertXSheet.showSuccess(this, "this is my success toast message")
                .setPosition(AlertPosition.BOTTOM)
                .setAnimation(AlertAnimationType.SLIDE_FROM_VERTICAL)
                .show()

        }

        btnWarn.setOnClickListener {
            AlertXSheet.showWarning(this, "this is my warning toast message, this is my warning toast message")
                .setAnimation(AlertAnimationType.SLIDE_FROM_LEFT)
                .show()
        }

        btnInfo.setOnClickListener {
            AlertXSheet.showInfo(this, "this is my info toast message, this is my info toast message")
                .show()
        }

        btnError.setOnClickListener {
            AlertXSheet.showError(this, "this is my error toast message, this is my error toast message, this is my error toast message, this is my error toast message")
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }

        btnCustom.setOnClickListener {
            var customStyle = MessageStyle(
                containerBackgroundColorRes = R.color.info,
                textColor = Color.CYAN,
                duration = 3000,
                showIcon = false,
                iconResource = R.drawable.ic_android_black_24,
                iconTint = Color.CYAN,
                animationType = AlertAnimationType.MORPH_FROM_BALL,
                position = AlertPosition.BOTTOM
            )
            AlertXSheet.showCustomMessage(this, "This is my custom message with custom style", customStyle)
        }

    }
}