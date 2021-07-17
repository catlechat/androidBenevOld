package com.ivan.benevold

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_sing_up_screen.*

class SingUpScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up_screen)
        singupScreenLayout.background = ContextCompat.getDrawable(
            this,
            R.drawable.backround_gradient
        )

        val oldUserCLick = findViewById<TextView>(R.id.oldUser)
        oldUserCLick.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }
    }
}