package com.ivan.benevold

import LoginResponse
import Network
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_create_task_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
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
        val dataResPrev = intent.extras?.get("data") as LoginResponse


        //Categories
        val spinner: Spinner = findViewById(R.id.contactSpinner)
        spinner.onItemSelectedListener = this
        val spinnerArray: MutableList<String> = ArrayList()

        GlobalScope.launch(Dispatchers.Default) {
            try {
                val dataRes = Network.api.categoriesAPICallAsync().await()
                if(dataRes.categories != null) {
                    withContext(Dispatchers.Main) {
                        dataRes.categories.forEach {
                            spinnerArray.add(it.nom_categorie.toString())
                        }
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    spinnerArray.add("Default")
                }
            }
        }
        val adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, spinnerArray
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter;

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
        var inputAddress = ""
        val inputDate = findViewById<CalendarView>(R.id.task_calendar)
        val inputHours = findViewById<EditText>(R.id.input_hours)
        val inputMinutes = findViewById<EditText>(R.id.input_minutes)


        //TODO : Faire un appel api pour recuperer l'addresse et le tel et l'email

        GlobalScope.launch(Dispatchers.Default) {
            val req = LoginRequest(dataResPrev.user_id.toString(), dataResPrev.token.toString())
            try {
                val dataRes = Network.api.userAPICallAsync(req).await()
                if(dataRes.nom != null) {
                    withContext(Dispatchers.Main) {
                        inputMail.setText(dataRes.email.toString())
                        if(dataRes.address != null)
                            inputAddress = dataRes.address.toString()
                        if(dataRes.city != null) {
                            val fullAddress = dataRes.address.toString() + " " + dataRes.city.toString()
                            inputAddress = fullAddress
                        }
                        if(dataRes.number != null)
                            inputPhone.setText(dataRes.number.toString())
                    }
                }
            } catch (e: HttpException) {
            }
        }

        val submit = findViewById<Button>(R.id.task_submit)
        submit.setOnClickListener {
            if(inputTitle.text.toString() != ""){
                val phone = if (!rPhone.isChecked) null else inputPhone.text.toString()
                val mail = if (!rMail.isChecked) null else inputMail.text.toString()
                val contact = if (!rContact.isChecked) null else inputContact.text.toString()
                val address = if (!rAddress.isChecked) null else inputAddress
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
                val intent = Intent(this, PreviewTaskScreen::class.java)
                intent.putExtra("TASK", task)
                intent.putExtra("data", dataResPrev);
                startActivity(intent)
            }else{
                Toast.makeText(this, resources.getString(R.string.atLeast), Toast.LENGTH_SHORT).show()
            }
        }
    }

    //FOR SPINNER
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}