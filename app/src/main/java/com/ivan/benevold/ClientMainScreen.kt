package com.ivan.benevold

import LoginResponse
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_client_main_screen.*

class ClientMainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main_screen)
        val dataResPrev = intent.extras?.get("data") as LoginResponse
        val messageOfDay = findViewById<TextView>(R.id.mainText)

        //
        ///TO DO : ADD THE MESSAGE OF THE DAY
        /*
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val dataRes = Network.api.messageAPICallAsync().await()
                if(dataRes.message != null) {
                    withContext(Dispatchers.Main) {
                        messageOfDay.text = dataRes.message
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    messageOfDay.text = resources.getString(R.string.messageOfTheDay)
                }
            }
        }
        */
        //
        //

        clientScreen.background = ContextCompat.getDrawable(
            this,
            R.drawable.backround_gradient
        )
        val profile = findViewById<Button>(R.id.profile)
        profile.setOnClickListener {
            val intent = Intent(this, ProfileScreen::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }

        val contact = findViewById<Button>(R.id.contact)
        contact.setOnClickListener {
            val intent = Intent(this, ContactUsScreen::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }

        val annonce = findViewById<Button>(R.id.newannoce)
        annonce.setOnClickListener {
            val intent = Intent(this, CreateTaskScreen::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }

        val myTask = findViewById<Button>(R.id.annonces)
        myTask.setOnClickListener {
            val intent = Intent(this, MyTasksScreen::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }
    }
}
