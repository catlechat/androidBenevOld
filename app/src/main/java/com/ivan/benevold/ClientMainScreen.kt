package com.ivan.benevold

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_client_main_screen.*

class ClientMainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main_screen)

        clientScreen.background = ContextCompat.getDrawable(
            this,
            R.drawable.backround_gradient
        )
        val profile = findViewById<Button>(R.id.profile)
        profile.setOnClickListener {
            val intent = Intent(this, ProfileScreen::class.java)
            startActivity(intent)
        }

        val contact = findViewById<Button>(R.id.contact)
        contact.setOnClickListener {
            val intent = Intent(this, ContactUsScreen::class.java)
            startActivity(intent)
        }

        val annonce = findViewById<Button>(R.id.newannoce)
        annonce.setOnClickListener {
            val intent = Intent(this, CreateTaskScreen::class.java)
            startActivity(intent)
        }


    }


}