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

class PreviewTaskScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_task_screen)

        val task = intent.getSerializableExtra("TASK") as? Task
        val dataResPrev = intent.extras?.get("data") as LoginResponse

        val title = findViewById<TextView>(R.id.title)
        val f_title = findViewById<TextView>(R.id.f_title)
        val name = findViewById<TextView>(R.id.name)
        val f_name = findViewById<TextView>(R.id.f_name)
        val address = findViewById<TextView>(R.id.address)
        val phone = findViewById<TextView>(R.id.phone)
        val email = findViewById<TextView>(R.id.email)
        val contact = findViewById<TextView>(R.id.contact)
        val description = findViewById<TextView>(R.id.description)
        val date = findViewById<TextView>(R.id.date)
        val time = findViewById<TextView>(R.id.hour)

        val picture = findViewById<ImageView>(R.id.picture)
        val picturef = findViewById<ImageView>(R.id.picturef)


        GlobalScope.launch(Dispatchers.Default) {
            val req = ProfileRequest(dataResPrev.user_id.toString())
            try {
                val dataRes = Network.api.userAPICallAsync(req, dataResPrev.token.toString()).await()
                if(dataRes.response?.nom != null) {
                    withContext(Dispatchers.Main) {
                        name.setText(dataRes.response.nom.toString())
                        f_name.setText(dataRes.response.nom.toString())
                        if(dataRes.response.picLink != null){
                            Picasso.get().load(dataRes.response.picLink.toString()).into(picture);
                            Picasso.get().load(dataRes.response.picLink.toString()).into(picturef);
                        }else{
                            picture.setImageResource(R.drawable.ic_baseline_person_24)
                            picturef.setImageResource(R.drawable.ic_baseline_person_24)
                        }
                    }
                }
            } catch (e: HttpException) {
            }
        }


        if (task != null) {
            title.text = task.title
            f_title.text = task.title

            if(task.address != null){
                address.text = task.address
            }else{
                address.visibility = View.GONE
            }
            if(task.phone != null){
                phone.text = task.phone
            }else{
                phone.visibility = View.GONE
            }
            if(task.contact != null){
                contact.text = task.contact
            }else{
                contact.visibility = View.GONE
            }
            if(task.email != null){
                email.text = task.email
            }else{
                email.visibility = View.GONE
            }
            if(task.description != null){
                description.text = task.description
            }else{
                description.visibility = View.GONE
            }
            if(task.date != null){
                date.text = task.date
            }else{
                date.visibility = View.GONE
            }
            if(task.time != null){
                time.text = task.time
            }else{
                time.visibility = View.GONE
            }
        }

        val submit = findViewById<Button>(R.id.submit)
        submit.setOnClickListener {
            val intent = Intent(this, ClientMainScreen::class.java)
            GlobalScope.launch(Dispatchers.Default) {

                val req = AnnonceRequest(
                        dataResPrev.user_id.toString(),
                        task?.title.toString(),
                        task?.category.toString(),
                        task?.description.toString(),
                        task?.phone.toString(),
                        task?.email.toString(),
                        task?.contact.toString(),
                        task?.address.toString(),
                        task?.date.toString(),
                        task?.time.toString())
                        try {
                    val dataRes = Network.api.annonceAPICallAsync(req, dataResPrev.token.toString()).await()
                    if(dataRes.requestCode.equals("200")) {
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

        val back = findViewById<Button>(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }
    }
}
class AnnonceRequest internal constructor(
        val user_id: String,
        val title:String,
        val category: String,
        val description: String,
        val phone:String,
        val email: String,
        val contact: String,
        val address:String,
        val date: String,
        val time: String
)