package com.ivan.benevold

import Network
import PasswordResponse
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
        val dataResPrev = intent.extras?.get("data") as PasswordResponse
        val passwordField = findViewById<TextInputEditText>(R.id.passwordField)
        val passwordField_bis = findViewById<TextInputEditText>(R.id.passwordField_bis)
        val sendClick = findViewById<Button>(R.id.change)

        sendClick.setOnClickListener {

            val passwordFieldTrimed = passwordField.text.toString().trim(' ')
            val passwordField_bisTrimed = passwordField_bis.text.toString().trim(' ')



            if(passwordFieldTrimed != "" && passwordField_bisTrimed != ""){
                if(passwordField.text.toString().equals(passwordField_bis.text.toString())){
                    val req = PasswordRequest(dataResPrev.user_id.toString(),passwordField.text.toString())
                    val intent = Intent(this, LoginScreen::class.java)
                    Toast.makeText(this, resources.getString(R.string.okPassword), Toast.LENGTH_LONG).show()
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
class PasswordRequest internal constructor(val user_id: String, val new_password: String)
