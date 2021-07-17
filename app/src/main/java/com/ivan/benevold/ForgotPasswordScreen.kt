package com.ivan.benevold

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_forgot_password_screen.*

class ForgotPasswordScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_screen)

        forgotPassword.background = ContextCompat.getDrawable(
            this,
            R.drawable.backround_gradient
        )

        val sendClick = findViewById<Button>(R.id.send)
        sendClick.setOnClickListener {
            val intent = Intent(this, ChangePassword::class.java)
            startActivity(intent)
        }

        val backClick = findViewById<Button>(R.id.back)
        backClick.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }
    }
}