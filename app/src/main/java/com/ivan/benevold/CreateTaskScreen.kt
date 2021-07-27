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
import kotlinx.android.synthetic.main.activity_more_details_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.sql.Date
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
        val dataResPrev = intent.extras?.get("data") as LoginResponse


        val back = findViewById<TextView>(R.id.backTask)
        back.setOnClickListener {
            val intent = Intent(this, ClientMainScreen::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }


        //Categories
        val spinner: Spinner = findViewById(R.id.contactSpinner)

        val spinnerArray: MutableList<String> = ArrayList()
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val dataRes = Network.api.categoriesAPICallAsync(dataResPrev.token.toString()).await()
                if(dataRes.response != null) {
                    withContext(Dispatchers.Main) {
                        dataRes.response.forEach {
                            spinnerArray.add(it.title.toString())
                        }
                        //
                        val adapter = ArrayAdapter(
                                this@CreateTaskScreen, android.R.layout.simple_spinner_item, spinnerArray
                        )
                        spinner.onItemSelectedListener = this@CreateTaskScreen
                        spinner.setSelection(0)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner.adapter = adapter;
                        //
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    spinnerArray.add("Default")
                }
            }
        }





           val inputTitle = findViewById<EditText>(R.id.input_title)
           val rPhone = findViewById<RadioButton>(R.id.r_phone)
           var inputPhone = findViewById<EditText>(R.id.input_phone)
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

        val calendar = Calendar.getInstance()
        inputDate.setOnDateChangeListener { view, year, month, dayOfMonth ->
                       // set the calendar date as calendar view selected date
                       calendar.set(year,month,dayOfMonth)
                       // set this date as calendar view selected date
            inputDate.date = calendar.timeInMillis
        }

        
           GlobalScope.launch(Dispatchers.Default) {
               val req = ProfileRequest(dataResPrev.user_id.toString())
               try {
                   val dataRes = Network.api.userAPICallAsync(req, dataResPrev.token.toString()).await()
                   if(dataRes.response?.nom != null) {
                       withContext(Dispatchers.Main) {
                           inputMail.setText(dataRes.response?.email.toString())
                           if(dataRes.response.adress != null){
                               inputAddress = dataRes.response?.adress.toString()

                           }
                           if(dataRes.response.city != null) {
                               val fullAddress = dataRes.response.adress.toString() + " " + dataRes.response.city.toString()
                               inputAddress = fullAddress
                           }
                           if(dataRes.response.phoneNumber != null) {
                               inputPhone.setText(dataRes.response.phoneNumber.toString())
                           }
                       }
                   }
               } catch (e: HttpException) {
               }
           }



           val submit = findViewById<Button>(R.id.task_submit)
           submit.setOnClickListener {

               val inputTitleTrimed = inputTitle.text.toString().trim(' ')


               if(inputTitleTrimed != ""){
                   val category = spinner.selectedItem.toString();

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
                           null,
                           null,
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