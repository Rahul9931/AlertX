package com.rahulsaini.alertxapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rahulsaini.alertx.alertXSheet.AlertXSheet
import com.rahulsaini.alertx.shared.model.AlertAnimationType
import com.rahulsaini.alertx.shared.model.AlertPosition

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

        // --- AlertX Sheet Demo ---

        // Morph - TOP
        findViewById<Button>(R.id.btn_sheet_morph).setOnClickListener {
            AlertXSheet.showInfoSheet(this, "Sheet Morph: Animation at the TOP")
                .setAnimation(AlertAnimationType.MORPH_FROM_BALL)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        // Success - TOP
        findViewById<Button>(R.id.btn_sheet_success).setOnClickListener {
            AlertXSheet.showSuccessSheet(this, "Sheet Success: Message sent at the TOP")
                .setAnimation(AlertAnimationType.SLIDE_FROM_VERTICAL)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        // Info - BOTTOM
        findViewById<Button>(R.id.btn_sheet_info).setOnClickListener {
            AlertXSheet.showInfoSheet(this, "Sheet Info: Update available at the BOTTOM")
                .setAnimation(AlertAnimationType.SLIDE_FROM_VERTICAL_BOUNCE)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }

        // Warning - TOP
        findViewById<Button>(R.id.btn_sheet_warn).setOnClickListener {
            AlertXSheet.showWarningSheet(this, "Sheet Warning: Storage almost full (TOP)")
                .setAnimation(AlertAnimationType.SLIDE_FROM_LEFT)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        // Error - BOTTOM
        findViewById<Button>(R.id.btn_sheet_error).setOnClickListener {
            AlertXSheet.showErrorSheet(this, "Sheet Error: Connection failed (BOTTOM)")
                .setAnimation(AlertAnimationType.SLIDE_FROM_RIGHT)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }

        // Zoom - TOP
        findViewById<Button>(R.id.btn_sheet_zoom).setOnClickListener {
            AlertXSheet.showInfoSheet(this, "Sheet Zoom: Focusing on Top details")
                .setAnimation(AlertAnimationType.ZOOM)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        // Fade - BOTTOM
        findViewById<Button>(R.id.btn_sheet_fade).setOnClickListener {
            AlertXSheet.showSuccessSheet(this, "Sheet Fade: Subtle appearance at BOTTOM")
                .setAnimation(AlertAnimationType.FADE)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }

        // Left Bounce - TOP
        findViewById<Button>(R.id.btn_sheet_left_bounce).setOnClickListener {
            AlertXSheet.showWarningSheet(this, "Sheet Left Bounce: Dynamic entry at TOP")
                .setAnimation(AlertAnimationType.SLIDE_FROM_LEFT_BOUNCE)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        // Right Bounce - BOTTOM
        findViewById<Button>(R.id.btn_sheet_right_bounce).setOnClickListener {
            AlertXSheet.showErrorSheet(this, "Sheet Right Bounce: Dynamic entry at BOTTOM")
                .setAnimation(AlertAnimationType.SLIDE_FROM_RIGHT_BOUNCE)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }
    }
}
