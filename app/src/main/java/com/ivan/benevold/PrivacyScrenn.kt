package com.ivan.benevold

import LoginResponse
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_privacy_screnn.*

class PrivacyScrenn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_screnn)

        val dataResPrev = intent.extras?.get("data") as LoginResponse


        privacyScreen.background = ContextCompat.getDrawable(
                this,
                R.drawable.backround_gradient
        )

        val back = findViewById<TextView>(R.id.backPolicy)
        back.setOnClickListener {
            val intent = Intent(this, ProfileScreen::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }


    }
}