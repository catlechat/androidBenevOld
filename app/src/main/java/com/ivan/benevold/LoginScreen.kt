package com.ivan.benevold

import Network
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

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


        val email = findViewById<TextInputEditText>(R.id.email)
        val password = findViewById<TextInputEditText>(R.id.password)
        val errotText = findViewById<TextView>(R.id.errorText)
        errotText.visibility = View.INVISIBLE
        val login = findViewById<Button>(R.id.loginButton)

        login.setOnClickListener {
            if(email.text.toString() != "" && password.text.toString() != ""){
                val intent = Intent(this, ClientMainScreen::class.java)
                GlobalScope.launch(Dispatchers.Default) {
                    val req = LoginRequest(email.text.toString(), password.text.toString())
                    try {
                        val dataRes = Network.api.loginAPICallAsync(req).await()
                        if(dataRes.user_id != null) {
                            withContext(Dispatchers.Main) {
                                intent.putExtra("data", dataRes)
                                startActivity(intent)
                            }
                        }
                    } catch (e: HttpException) {
                        withContext(Dispatchers.Main) {
                            errotText.visibility = View.VISIBLE
                            errotText.text = resources.getString(R.string.mailWrong)
                        }
                    }
                }
            }else{
                errotText.visibility = View.VISIBLE;
                errotText.text = resources.getString(R.string.loginFields)
            }

        }

    }
}
class LoginRequest internal constructor(val login: String, val password: String)