package com.rahulsaini.alertxapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rahulsaini.alertx.AlertXToast
import com.rahulsaini.alertx.topDialog.TopAlertX

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
        btnSuccess.setOnClickListener {
//            AlertXToast.show(this, "my custom toast")
            TopAlertX.showSuccess(this, "success message sdnfoids iosndbfgiw oiewrfi dfiub fewui ds efiewoi dsf in weids wsegf iwenf popew dsaowe weifna")

        }

        btnInfo.setOnClickListener {
            TopAlertX.showInfo(this, "Info message jbjbiu  ujivyuyu buuih")
        }

        btnError.setOnClickListener {
            TopAlertX.showError(this, "Error messagejk jkbb")
        }

    }
}