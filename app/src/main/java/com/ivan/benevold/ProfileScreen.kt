package com.ivan.benevold

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_screen)

        val logout = findViewById<Button>(R.id.logOut)
        logout.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }

        val back = findViewById<TextView>(R.id.top)
        back.setOnClickListener {
            val intent = Intent(this, ClientMainScreen::class.java)
            startActivity(intent)
        }

        val sett = findViewById<Button>(R.id.profileSettings)
        sett.setOnClickListener {
            val intent = Intent(this, EditProfileScreen::class.java)
            startActivity(intent)
        }

        val contact = findViewById<Button>(R.id.contactUs)
        contact.setOnClickListener {
            val intent = Intent(this, ContactUsScreen::class.java)
            startActivity(intent)
        }

        val privacy = findViewById<Button>(R.id.privacy)
        privacy.setOnClickListener {
            val intent = Intent(this, PrivacyScrenn::class.java)
            startActivity(intent)
        }

        val information = findViewById<Button>(R.id.information)
        information.setOnClickListener {
            val intent = Intent(this, InformationScreen::class.java)
            startActivity(intent)
        }
    }
}