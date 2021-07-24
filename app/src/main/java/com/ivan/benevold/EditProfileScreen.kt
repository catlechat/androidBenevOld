package com.ivan.benevold

import LoginResponse
import Network
import android.content.Intent
import android.os.Bundle
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


        val fullName = findViewById<TextInputEditText>(R.id.fullNameField)
        val email = findViewById<TextInputEditText>(R.id.emailField)
        val address = findViewById<TextInputEditText>(R.id.addressField)
        val city = findViewById<TextInputEditText>(R.id.cityField)
        val phone = findViewById<TextInputEditText>(R.id.phoneField)
        val picLink = findViewById<TextInputEditText>(R.id.pictureField)
        val pic = findViewById<ImageView>(R.id.imageView1)


        ///call api to get user ifos and fill them to the things
        GlobalScope.launch(Dispatchers.Default) {
            val req = LoginRequest(dataResPrev.user_id.toString(), dataResPrev.token.toString())
            try {
                val dataRes = Network.api.userAPICallAsync(req).await()
                if(dataRes.nom != null) {
                    withContext(Dispatchers.Main) {
                        fullName.setText(dataRes.nom.toString())
                        email.setText(dataRes.email.toString())
                        if(dataRes.address != null)
                            address.setText(dataRes.address.toString())
                        if(dataRes.city != null)
                            city.setText(dataRes.city.toString())
                        if(dataRes.number != null)
                            phone.setText(dataRes.number.toString())
                        if(dataRes.picLink != null){
                            Picasso.get().load(dataRes.picLink.toString()).into(pic);
                            picLink.setText(dataRes.picLink.toString())
                        }else{
                            pic.setImageResource(R.drawable.ic_baseline_person_24)
                        }
                    }
                }
            } catch (e: HttpException) {
            }
        }
        /////


        val back = findViewById<TextView>(R.id.backText)
        back.setOnClickListener {
            val intent = Intent(this, ProfileScreen::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }
        val save = findViewById<TextView>(R.id.saveText)
        save.setOnClickListener {
            val intent = Intent(this, ProfileScreen::class.java)
            GlobalScope.launch(Dispatchers.Default) {
                val req = UpdateRequest(
                        dataResPrev.user_id.toString(),
                        dataResPrev.token.toString(),
                        fullName.text.toString(),
                        address.text.toString(),
                        city.text.toString(),
                        email.text.toString(),
                        phone.text.toString(),
                        picLink.text.toString()
                        )
                try {
                    val dataRes = Network.api.userUpdateAPICallAsync(req).await()
                    if(dataRes.requestCode.toString().equals(200)) {
                        withContext(Dispatchers.Main) {
                            intent.putExtra("data", dataResPrev);
                            startActivity(intent)
                        }
                    }
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                    }
                }
            }
        }
    }
}
data class UpdateRequest(
        @SerializedName("id_user")
        val id_user:String?,
        @SerializedName("token")
        val token:String?,
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