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
import kotlinx.android.synthetic.main.activity_forgot_password_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ForgotPasswordScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_screen)

        forgotPassword.background = ContextCompat.getDrawable(
            this,
            R.drawable.backround_gradient
        )

        val errorText = findViewById<TextView>(R.id.errorText)
        errorText.visibility = View.INVISIBLE;

        val email = findViewById<TextInputEditText>(R.id.emailInput)
        val sendClick = findViewById<Button>(R.id.send)

        sendClick.setOnClickListener {
            if(email.text.toString() != ""){
                val intent = Intent(this, ChangePassword::class.java)
                GlobalScope.launch(Dispatchers.Default) {
                    val req = EmailRequest(email.text.toString())
                    try {
                        val dataRes = Network.api.emailAPICallAsync(req).await()
                        if(dataRes.user_id != null) {
                            withContext(Dispatchers.Main) {
                                intent.putExtra("data", dataRes);
                                startActivity(intent)
                            }
                        }
                    } catch (e: HttpException) {
                        withContext(Dispatchers.Main) {
                            errorText.visibility = android.view.View.VISIBLE;
                            errorText.text = resources.getString(R.string.mailExist);
                        }
                    }
                }
            }else{
                errorText.visibility = View.VISIBLE;
                errorText.text = this.resources.getString(R.string.mailExist);
            }
        }


        val backClick = findViewById<Button>(R.id.back)
        backClick.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }
    }
}
class EmailRequest internal constructor(val email: String)