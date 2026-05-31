package com.rahulsaini.alertxapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rahulsaini.alertx.alertXSheet.AlertXSheet
import com.rahulsaini.alertx.alertXToast.AlertXToast
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

        setupSheetDemo()
        setupToastDemo()
    }

    private fun setupSheetDemo() {
        // --- AlertX Sheet Listeners ---
        findViewById<Button>(R.id.btn_sheet_morph).setOnClickListener {
            AlertXSheet.showInfoSheet(this, "Sheet Morph: Animation at the TOP")
                .setAnimation(AlertAnimationType.MORPH_FROM_BALL)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        findViewById<Button>(R.id.btn_sheet_success).setOnClickListener {
            AlertXSheet.showSuccessSheet(this, "Sheet Success: Operation successful at the TOP")
                .setAnimation(AlertAnimationType.SLIDE_FROM_VERTICAL)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        findViewById<Button>(R.id.btn_sheet_info).setOnClickListener {
            AlertXSheet.showInfoSheet(this, "Sheet Info: Update available at the BOTTOM")
                .setAnimation(AlertAnimationType.SLIDE_FROM_VERTICAL_BOUNCE)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }

        findViewById<Button>(R.id.btn_sheet_warn).setOnClickListener {
            AlertXSheet.showWarningSheet(this, "Sheet Warning: Storage almost full (TOP)")
                .setAnimation(AlertAnimationType.SLIDE_FROM_LEFT)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        findViewById<Button>(R.id.btn_sheet_error).setOnClickListener {
            AlertXSheet.showErrorSheet(this, "Sheet Error: Connection failed (BOTTOM)")
                .setAnimation(AlertAnimationType.SLIDE_FROM_RIGHT)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }

        findViewById<Button>(R.id.btn_sheet_zoom).setOnClickListener {
            AlertXSheet.showInfoSheet(this, "Sheet Zoom: Focusing on Top details")
                .setAnimation(AlertAnimationType.ZOOM)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        findViewById<Button>(R.id.btn_sheet_fade).setOnClickListener {
            AlertXSheet.showSuccessSheet(this, "Sheet Fade: Subtle appearance at BOTTOM")
                .setAnimation(AlertAnimationType.FADE)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }

        findViewById<Button>(R.id.btn_sheet_left_bounce).setOnClickListener {
            AlertXSheet.showWarningSheet(this, "Sheet Left Bounce: Dynamic entry at TOP")
                .setAnimation(AlertAnimationType.SLIDE_FROM_LEFT_BOUNCE)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        findViewById<Button>(R.id.btn_sheet_right_bounce).setOnClickListener {
            AlertXSheet.showErrorSheet(this, "Sheet Right Bounce: Dynamic entry at BOTTOM")
                .setAnimation(AlertAnimationType.SLIDE_FROM_RIGHT_BOUNCE)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }
    }

    private fun setupToastDemo() {
        // --- AlertX Toast Listeners ---
        findViewById<Button>(R.id.btn_toast_morph).setOnClickListener {
            AlertXToast.showInfoToast(this, "Toast Morph: Animation at the TOP")
                .setAnimation(AlertAnimationType.MORPH_FROM_BALL)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        findViewById<Button>(R.id.btn_toast_success).setOnClickListener {
            AlertXToast.showSuccessToast(this, "Toast Success: Saved at the TOP")
                .setAnimation(AlertAnimationType.SLIDE_FROM_VERTICAL)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        findViewById<Button>(R.id.btn_toast_info).setOnClickListener {
            AlertXToast.showInfoToast(this, "Toast Info: Update available at the BOTTOM")
                .setAnimation(AlertAnimationType.SLIDE_FROM_VERTICAL_BOUNCE)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }

        findViewById<Button>(R.id.btn_toast_warn).setOnClickListener {
            AlertXToast.showWarningToast(this, "Toast Warning: Storage full (TOP)")
                .setAnimation(AlertAnimationType.SLIDE_FROM_LEFT)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        findViewById<Button>(R.id.btn_toast_error).setOnClickListener {
            AlertXToast.showErrorToast(this, "Toast Error: Sync failed (BOTTOM)")
                .setAnimation(AlertAnimationType.SLIDE_FROM_RIGHT)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }

        findViewById<Button>(R.id.btn_toast_zoom).setOnClickListener {
            AlertXToast.showInfoToast(this, "Toast Zoom: Scaling at the TOP")
                .setAnimation(AlertAnimationType.ZOOM)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        findViewById<Button>(R.id.btn_toast_fade).setOnClickListener {
            AlertXToast.showSuccessToast(this, "Toast Fade: Subtle entry at BOTTOM")
                .setAnimation(AlertAnimationType.FADE)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }

        findViewById<Button>(R.id.btn_toast_left_bounce).setOnClickListener {
            AlertXToast.showWarningToast(this, "Toast Left Bounce: Entry at TOP")
                .setAnimation(AlertAnimationType.SLIDE_FROM_LEFT_BOUNCE)
                .setPosition(AlertPosition.TOP)
                .show()
        }

        findViewById<Button>(R.id.btn_toast_right_bounce).setOnClickListener {
            AlertXToast.showErrorToast(this, "Toast Right Bounce: Entry at BOTTOM")
                .setAnimation(AlertAnimationType.SLIDE_FROM_RIGHT_BOUNCE)
                .setPosition(AlertPosition.BOTTOM)
                .show()
        }
    }
}

/*
--- GLOBAL CONFIGURATION GUIDE (APPLICATION CLASS) ---

To set default styles globally so you don't have to configure them every time:

1. Create a class that extends 'android.app.Application'.
2. Register it in your AndroidManifest.xml: <application android:name=".MyApplication" ... />
3. Initialize the library in onCreate().

// --- Example Application Class Code ---
/*
import android.app.Application
import android.graphics.Color
import com.rahulsaini.alertx.alertXToast.AlertXToast
import com.rahulsaini.alertx.alertXSheet.AlertXSheet
import com.rahulsaini.alertx.shared.model.AlertAnimationType
import com.rahulsaini.alertx.shared.model.AlertPosition

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Configure default styles for all Toasts globally
        AlertXToast.initialize(this) {
            successStyle = successStyle.copy(
                textColor = Color.WHITE,
                duration = 2000
            )
            errorStyle = errorStyle.copy(
                containerBackgroundColorRes = android.R.color.holo_red_dark
            )
        }

        // Configure default styles for all Sheets globally
        AlertXSheet.initialize(this) {
            infoStyle = infoStyle.copy(
                animationType = AlertAnimationType.MORPH_FROM_BALL,
                position = AlertPosition.TOP
            )
        }
    }
}
*/
*/
