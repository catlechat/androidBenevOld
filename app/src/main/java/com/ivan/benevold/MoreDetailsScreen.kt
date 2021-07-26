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
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.Serializable

class MoreDetailsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_details_screen)

        val userToken = intent.extras?.get("token") as String
        val taskID = intent.extras?.get("id") as String
        var userID = ""

        val title = findViewById<TextView>(R.id.a_title)
        val name = findViewById<TextView>(R.id.a_name)
        val address = findViewById<TextView>(R.id.a_address)
        val phone = findViewById<TextView>(R.id.a_phone)
        val email = findViewById<TextView>(R.id.a_email)
        val contact = findViewById<TextView>(R.id.a_contact)
        val description = findViewById<TextView>(R.id.a_description)
        val date = findViewById<TextView>(R.id.a_date)
        val time = findViewById<TextView>(R.id.a_hour)
        val picture = findViewById<ImageView>(R.id.a_picture)

        //on fait l'appel a l'api pour recuperer les info de l'annonce


        GlobalScope.launch(Dispatchers.Default) {
            val req = AnnonceUpdateRequest(taskID)
            try {
                val dataRes = Network.api.annonceAPICallAsync(req,userToken).await()
                if(dataRes.response != null) {
                    withContext(Dispatchers.Main) {
                        userID = dataRes.response.user_id.toString()
                        title.text = dataRes.response.title

                        if(dataRes.response.address.toString() != "null"){
                            address.text = dataRes.response.address
                        }else{
                            address.visibility = View.GONE
                        }
                        if(dataRes.response.phone.toString() != "null"){
                            phone.text = dataRes.response.phone
                        }else{
                            phone.visibility = View.GONE
                        }
                        if(dataRes.response.contact.toString() != "null"){
                            contact.text = dataRes.response.contact
                        }else{
                            contact.visibility = View.GONE
                        }
                        if(dataRes.response.email.toString() != "null"){
                            email.text = dataRes.response.email
                        }else{
                            email.visibility = View.GONE
                        }
                        if(dataRes.response.description.toString() != "null"){
                            description.text = dataRes.response.description
                        }else{
                            description.visibility = View.GONE
                        }
                        if(dataRes.response.date.toString() != "null"){
                            date.text = dataRes.response.date
                        }else{
                            date.visibility = View.GONE
                        }
                        if(dataRes.response.time.toString() != "null"){
                            time.text = dataRes.response.time
                        }else{
                            time.visibility = View.GONE
                        }
                    }
                }
            } catch (e: HttpException) {
            }
            val request = ProfileRequest(userID)
            try {
                val dataRes = Network.api.userAPICallAsync(request,userToken).await()
                if(dataRes.response?.nom != null) {
                    withContext(Dispatchers.Main) {
                        name.setText(dataRes.response.nom.toString())
                        if(dataRes.response.picLink != null){
                            Picasso.get().load(dataRes.response.picLink.toString()).into(picture);
                        }else{
                            picture.setImageResource(R.drawable.ic_baseline_person_24)
                        }
                    }
                }
            } catch (e: HttpException) {
            }
        }






        val back = findViewById<Button>(R.id.backMore)
        back.setOnClickListener {
            onBackPressed()
        }
        val remove = findViewById<Button>(R.id.remove)
        remove.setOnClickListener {
            GlobalScope.launch(Dispatchers.Default) {
                val req = AnnonceUpdateRequest(taskID)
                try {
                    val dataRes = Network.api.annonceUpdateAPICallAsync(
                            req,
                            userToken
                    ).await()
                    if(dataRes.requestCode == "200") {
                        val intent = Intent(this@MoreDetailsScreen, MyTasksScreen::class.java)
                        intent.putExtra("data", LoginResponse(
                                userID,
                                userToken,
                                null
                        ));
                        startActivity(intent)
                    }
                } catch (e: HttpException) {
                }
            }
        }

    }
}
data class AnnonceUpdateRequest(
        @SerializedName("annonce_id")
        val annonce_id:String?
) : Serializable