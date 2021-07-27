package com.ivan.benevold

import Network
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_sing_up_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException


class SingUpScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up_screen)
        singupScreenLayout.background = ContextCompat.getDrawable(
            this,
            R.drawable.backround_gradient
        )

        val fullNameString = findViewById<TextInputEditText>(R.id.fullNameField)
        val loginString = findViewById<TextInputEditText>(R.id.loginField)
        val passwordString = findViewById<TextInputEditText>(R.id.passwordField)
        val errorText = findViewById<TextView>(R.id.errorText)


        errorText.visibility = View.INVISIBLE;

        val oldUserCLick = findViewById<TextView>(R.id.oldUser)
        oldUserCLick.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }

        val singupClick = findViewById<TextView>(R.id.sungupButton)
        singupClick.setOnClickListener {

            val fullNameTrimed = fullNameString.text.toString().trim(' ')
            val loginTrimed = loginString.text.toString().trim(' ')
            val passwordTrimed = passwordString.text.toString().trim(' ')


            if(fullNameTrimed != "" && loginTrimed != "" && passwordTrimed != ""){
                val intent = Intent(this, ClientMainScreen::class.java)
                GlobalScope.launch(Dispatchers.Default) {
                    val req = SingupRequest(fullNameString.text.toString(),loginString.text.toString(),passwordString.toString())
                    try {
                        val dataRes = Network.api.singupAPICallAsync(req).await()
                        if(dataRes.user_id != null) {
                            withContext(Dispatchers.Main) {
                                intent.putExtra("data", dataRes);
                                startActivity(intent)
                            }
                        }
                    } catch (e: HttpException) {
                        withContext(Dispatchers.Main) {
                            errorText.visibility = View.VISIBLE;
                            errorText.text = resources.getString(R.string.mailDoesExist)
                        }
                    }
                }
            }else{
                errorText.visibility = View.VISIBLE;
                errorText.text = resources.getString(R.string.singupFields)
            }
        }
    }
}
class SingupRequest internal constructor(val fullName: String, val email: String, val password:String)