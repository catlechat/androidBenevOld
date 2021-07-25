package com.ivan.benevold

import LoginResponse
import Network
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ProfileScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_screen)

        val dataResPrev = intent.extras?.get("data") as LoginResponse

        val fullName = findViewById<TextView>(R.id.nameField)
        val email = findViewById<TextView>(R.id.emailField)
        val address = findViewById<TextView>(R.id.addressField)
        val city = findViewById<TextView>(R.id.cityField)
        val phone = findViewById<TextView>(R.id.phoneField)
        val picLink = findViewById<ImageView>(R.id.imageView1)


        GlobalScope.launch(Dispatchers.Default) {
            val req = ProfileRequest(dataResPrev.user_id.toString())
            try {
                val dataRes = Network.api.userAPICallAsync(req,dataResPrev.token.toString()).await()
                if(dataRes.response?.nom != null) {
                    withContext(Dispatchers.Main) {
                        fullName.text = dataRes.response.nom.toString()
                        email.text = dataRes.response.email.toString()
                        if(dataRes.response.adress != null){
                            address.text = dataRes.response?.adress.toString()

                        }else{
                            address.visibility = View.INVISIBLE
                        }
                        if(dataRes.response.city != null){
                            city.text = dataRes.response.city.toString()

                        }else{
                            city.visibility = View.INVISIBLE
                        }
                        if(dataRes.response.phoneNumber != null){
                            phone.text = dataRes.response.phoneNumber.toString()

                        }else{
                            phone.visibility = View.INVISIBLE
                        }
                        if(dataRes.response.picLink != null && dataRes.response.picLink.toString() != "" && dataRes.response.picLink.toString() != "pas d'image" ){
                            Picasso.get().load(dataRes.response.picLink.toString()).into(picLink);
                        }else{
                            picLink.setImageResource(R.drawable.ic_baseline_person_24)
                        }
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    fullName.text = resources.getString(R.string.errorOn)
                    email.text = resources.getString(R.string.errorOn)
                    address.text = resources.getString(R.string.errorOn)
                    phone.text = resources.getString(R.string.errorOn)
                    city.text = resources.getString(R.string.errorOn)
                    picLink.setImageResource(R.drawable.ic_baseline_person_24)
                }
            }
        }



        val logout = findViewById<Button>(R.id.logOut)
        logout.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }

        val back = findViewById<TextView>(R.id.top)
        back.setOnClickListener {
            val intent = Intent(this, ClientMainScreen::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }

        val sett = findViewById<Button>(R.id.profileSettings)
        sett.setOnClickListener {
            val intent = Intent(this, EditProfileScreen::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }

        val contact = findViewById<Button>(R.id.contactUs)
        contact.setOnClickListener {
            val intent = Intent(this, ContactUsScreen::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }

        val privacy = findViewById<Button>(R.id.privacy)
        privacy.setOnClickListener {
            val intent = Intent(this, PrivacyScrenn::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }

        val information = findViewById<Button>(R.id.information)
        information.setOnClickListener {
            val intent = Intent(this, InformationScreen::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }
    }
}
class ProfileRequest internal constructor(val user_id: String)