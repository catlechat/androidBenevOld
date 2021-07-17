package com.ivan.benevold

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_create_task_screen.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreateTaskScreen : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task_screen)

        createTaskScreenLayout.background = ContextCompat.getDrawable(
                this,
                R.drawable.backround_gradient
        )

        val back = findViewById<TextView>(R.id.backTask)
        back.setOnClickListener {
            val intent = Intent(this, ClientMainScreen::class.java)
            startActivity(intent)
        }

        //Categories
        val spinner: Spinner = findViewById(R.id.contactSpinner)
        spinner.onItemSelectedListener = this
        val spinnerArray: MutableList<String> = ArrayList()
        spinnerArray.add("Shopping")
        spinnerArray.add("Baby Sitting")
        spinnerArray.add("Chilling")
        spinnerArray.add("Meds")
        val adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, spinnerArray
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter;
        ///

        ///ici je vais ecrire le code qui va generer une tache
        ///        val task = Task(...,...,...)

        val inputTitle = findViewById<EditText>(R.id.input_title)
        val category = spinner.selectedItem.toString();
        val rPhone = findViewById<RadioButton>(R.id.r_phone)
        val inputPhone = findViewById<EditText>(R.id.input_phone)
        val rMail = findViewById<RadioButton>(R.id.r_mail)
        val inputMail = findViewById<EditText>(R.id.input_email)
        val rContact = findViewById<RadioButton>(R.id.r_contact)
        val inputContact = findViewById<EditText>(R.id.input_contact)
        val inputDescription = findViewById<EditText>(R.id.input_description)
        val rAddress = findViewById<CheckBox>(R.id.r_address)
        val inputDate = findViewById<CalendarView>(R.id.task_calendar)
        val inputHours = findViewById<EditText>(R.id.input_hours)
        val inputMinutes = findViewById<EditText>(R.id.input_minutes)


        val submit = findViewById<Button>(R.id.task_submit)
        submit.setOnClickListener {
            val phone = if (!rPhone.isChecked) null else inputPhone.text.toString()
            val mail = if (!rMail.isChecked) null else inputMail.text.toString()
            val contact = if (!rContact.isChecked) null else inputContact.text.toString()
            val address = if (!rAddress.isChecked) null else "8 Rue Oger"
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val selectedDate = formatter.format(Date(inputDate.date))
            val description = if (inputDescription.text.toString() == "") null else inputDescription.text.toString()
            val hours = if (inputHours.text.toString() == "") null else inputHours.text.toString()
            val minutes = if (inputMinutes.text.toString() == "") null else inputMinutes.text.toString()
            val time = if (hours != null && minutes != null) "${hours}:${minutes}" else null

            val task = Task(
                    inputTitle.text.toString(),
                    category,
                    description,
                    phone,
                    mail,
                    contact,
                    address,
                    selectedDate,
                    time
            )

            //val test = findViewById<TextView>(R.id.questionforTitle)
            //test.text = task.toString()

            val intent = Intent(this, PreviewTaskScreen::class.java)
            //TODO: ajouter le user dans le extra
            intent.putExtra("TASK", task)
            startActivity(intent)

        }



    }

    //FOR SPINNER
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}