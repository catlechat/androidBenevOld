package com.ivan.benevold

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ContactUsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us_screen)

        val back = findViewById<TextView>(R.id.contactBack)
        back.setOnClickListener {
            val intent = Intent(this, ProfileScreen::class.java)
            startActivity(intent)
        }
    }
}