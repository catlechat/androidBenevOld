package com.ivan.benevold

import LoginResponse
import Network
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.Serializable

class EditProfileScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_screen)
        val dataResPrev = intent.extras?.get("data") as LoginResponse


        val fullName = findViewById<TextInputEditText>(R.id.nameField)
        val email = findViewById<TextInputEditText>(R.id.emailFieldUser)
        val address = findViewById<TextInputEditText>(R.id.addressFieldUser)
        val city = findViewById<TextInputEditText>(R.id.cityFieldUser)
        val phone = findViewById<TextInputEditText>(R.id.phoneFieldUser)
        val picLink = findViewById<TextInputEditText>(R.id.pictureFieldUser)
        val pic = findViewById<ImageView>(R.id.imageView1)

        GlobalScope.launch(Dispatchers.Default) {
            val req = ProfileRequest(dataResPrev.user_id.toString())
            try {
                val dataRes = Network.api.userAPICallAsync(req,dataResPrev.token.toString()).await()
                if(dataRes.response?.nom != null) {
                    withContext(Dispatchers.Main) {
                        fullName.setText(dataRes.response.nom.toString())
                        email.setText(dataRes.response.email.toString())
                        if(dataRes.response.adress != null)
                            address.setText(dataRes.response.adress.toString())
                        if(dataRes.response.city != null)
                            city.setText(dataRes.response.city.toString())
                        if(dataRes.response.phoneNumber != null)
                            phone.setText(dataRes.response.phoneNumber.toString())
                        if(dataRes.response.picLink != null && dataRes.response.picLink.toString() != ""){
                            Picasso.get().load(dataRes.response.picLink.toString()).into(pic);
                            picLink.setText(dataRes.response.picLink.toString())
                        }else{
                            pic.setImageResource(R.drawable.ic_baseline_person_24)
                        }
                    }
                }
            } catch (e: HttpException) {
            }
        }

        val back = findViewById<TextView>(R.id.backText)
        back.setOnClickListener {
            val intent = Intent(this, ProfileScreen::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }

        val save = findViewById<Button>(R.id.saveText)
        save.setOnClickListener {
            val intent = Intent(this, ProfileScreen::class.java)
            GlobalScope.launch(Dispatchers.Default) {


                ///TRIME SPACE
                var fullNameTrime = fullName.text.toString().trim(' ')
                var emailTrime = email.text.toString().trim(' ')
                var addressTrime = address.text.toString().trim(' ')
                var cityTrime = city.text.toString().trim(' ')
                var phoneTrime = phone.text.toString().trim(' ')
                var picLinkTrime = picLink.text.toString().trim(' ')


                if(fullNameTrime == ""){
                    fullNameTrime = "pas de nom"
                }
                if(addressTrime == ""){
                    addressTrime = "pas d'addresse"
                }
                if(cityTrime == ""){
                    cityTrime = "pas de ville"
                }
                if(emailTrime == ""){
                    emailTrime = "pas de mail"
                }
                if(phoneTrime == ""){
                    phoneTrime = "pas de numero"
                }
                if(picLinkTrime == ""){
                    picLinkTrime = "pas d'image"
                }

                val req = UpdateRequest(
                        dataResPrev.user_id,
                        fullNameTrime,
                        addressTrime,
                        cityTrime,
                        emailTrime,
                        phoneTrime,
                        picLinkTrime
                        )
                try {
                    val dataRes = Network.api.userUpdateAPICallAsync(req,dataResPrev.token.toString()).await()
                    withContext(Dispatchers.Main) {
                        intent.putExtra("data", dataResPrev);
                        startActivity(intent)
                    }
                } catch (e: HttpException) {
                }

            }
        }
    }
}
data class UpdateRequest(
        @SerializedName("id_user")
        val id_user:String?,
        @SerializedName("nom")
        val nom:String?,
        @SerializedName("address")
        val address:String?,
        @SerializedName("city")
        val city:String?,
        @SerializedName("email")
        val email:String?,
        @SerializedName("number")
        val number:String?,
        @SerializedName("picLink")
        val picLink:String?
) : Serializable