package com.ivan.benevold

import Network
import PasswordRequest
import PasswordResponse
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangePassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        newPassword.background = ContextCompat.getDrawable(
            this,
            R.drawable.backround_gradient
        )
        val errorText = findViewById<TextView>(R.id.errorText)
        errorText.visibility = View.INVISIBLE;
        val dataRes = intent.extras?.get("data") as PasswordResponse
        val passwordField = findViewById<TextInputEditText>(R.id.passwordField)
        val passwordField_bis = findViewById<TextInputEditText>(R.id.passwordField_bis)
        val sendClick = findViewById<Button>(R.id.change)

        sendClick.setOnClickListener {
            if(passwordField.text.toString() != "" && passwordField_bis.text.toString() != ""){
                if(passwordField.text.toString().equals(passwordField_bis.text.toString())){
                    val req = PasswordRequest(dataRes.user_id.toString(),passwordField.text.toString())
                    val intent = Intent(this, LoginScreen::class.java)

                    GlobalScope.launch(Dispatchers.Default) {
                        val dataRes = Network.api.passwordAPICallAsync(req).await()
                        if(dataRes.user_id != null){
                            withContext(Dispatchers.Main) {
                                startActivity(intent)
                            }
                        }
                    }
                }else{
                    errorText.visibility = View.VISIBLE;
                    errorText.text = this.resources.getString(R.string.passwordMatch);
                }
            }else{
                errorText.visibility = View.VISIBLE;
                errorText.text = this.resources.getString(R.string.passwordTwice);
            }
        }

        val backClick = findViewById<Button>(R.id.back)
        backClick.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }
    }


}