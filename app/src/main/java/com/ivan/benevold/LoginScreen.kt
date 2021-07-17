package com.ivan.benevold

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_login_screen.*

class LoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        loginScreenLayout.background = ContextCompat.getDrawable(
            this,
            R.drawable.backround_gradient
        )

        val newUserCLick = findViewById<TextView>(R.id.newUser)
        newUserCLick.setOnClickListener {
            val intent = Intent(this, SingUpScreen::class.java)
            startActivity(intent)
        }

        val forgotClick = findViewById<TextView>(R.id.forgotThePasswordText)
        forgotClick.setOnClickListener {
            val intent = Intent(this, ForgotPasswordScreen::class.java)
            startActivity(intent)
        }

        val login = findViewById<Button>(R.id.loginButton)
        login.setOnClickListener {
            val intent = Intent(this, ClientMainScreen::class.java)
            startActivity(intent)
        }

    }
}