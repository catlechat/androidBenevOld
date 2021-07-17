package com.ivan.benevold

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PreviewTaskScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_task_screen)

        val task = intent.getSerializableExtra("TASK") as? Task

        //Toast.makeText(this,task.phone, Toast.LENGTH_LONG).show()

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


        if (task != null) {
            title.text = task.title
            f_title.text = task.title
            name.text = "Ivan Ceaicovschi"
            f_name.text = "Ivan Ceaicovschi"

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
        val back = findViewById<TextView>(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }
    }
}