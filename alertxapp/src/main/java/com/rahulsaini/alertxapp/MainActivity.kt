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

        // --- AlertX Sheet Demo (Only) ---

        // 1. Morph - TOP (Featured Best Animation)
        findViewById<Button>(R.id.btn_sheet_morph).setOnClickListener {
            AlertXSheet.showInfoSheet(this, "Sheet Morph: Our best animation at the TOP!")
                .setAnimation(AlertAnimationType.MORPH_FROM_BALL)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        // 2. Success - TOP (Slide Vertical)
        findViewById<Button>(R.id.btn_sheet_success).setOnClickListener {
            AlertXSheet.showSuccessSheet(this, "Sheet Success: Operation completed successfully!")
                .setAnimation(AlertAnimationType.SLIDE_FROM_VERTICAL)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        // 3. Info - BOTTOM (Vertical Bounce)
        findViewById<Button>(R.id.btn_sheet_info).setOnClickListener {
            AlertXSheet.showInfoSheet(this, "Sheet Info: New update available at the BOTTOM.")
                .setAnimation(AlertAnimationType.SLIDE_FROM_VERTICAL_BOUNCE)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }

        // 4. Warning - TOP (Slide Left)
        findViewById<Button>(R.id.btn_sheet_warn).setOnClickListener {
            AlertXSheet.showWarningSheet(this, "Sheet Warning: Please check your settings (TOP).")
                .setAnimation(AlertAnimationType.SLIDE_FROM_LEFT)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        // 5. Error - BOTTOM (Slide Right)
        findViewById<Button>(R.id.btn_sheet_error).setOnClickListener {
            AlertXSheet.showErrorSheet(this, "Sheet Error: Something went wrong (BOTTOM).")
                .setAnimation(AlertAnimationType.SLIDE_FROM_RIGHT)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }

        // 6. Zoom - TOP
        findViewById<Button>(R.id.btn_sheet_zoom).setOnClickListener {
            AlertXSheet.showInfoSheet(this, "Sheet Zoom: Focusing on Top details.")
                .setAnimation(AlertAnimationType.ZOOM)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        // 7. Fade - BOTTOM
        findViewById<Button>(R.id.btn_sheet_fade).setOnClickListener {
            AlertXSheet.showSuccessSheet(this, "Sheet Fade: Subtle appearance at BOTTOM.")
                .setAnimation(AlertAnimationType.FADE)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }

        // 8. Left Bounce - TOP
        findViewById<Button>(R.id.btn_sheet_left_bounce).setOnClickListener {
            AlertXSheet.showWarningSheet(this, "Sheet Left Bounce: Dynamic entry at TOP.")
                .setAnimation(AlertAnimationType.SLIDE_FROM_LEFT_BOUNCE)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        // 9. Right Bounce - BOTTOM
        findViewById<Button>(R.id.btn_sheet_right_bounce).setOnClickListener {
            AlertXSheet.showErrorSheet(this, "Sheet Right Bounce: Dynamic entry at BOTTOM.")
                .setAnimation(AlertAnimationType.SLIDE_FROM_RIGHT_BOUNCE)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }
    }
}