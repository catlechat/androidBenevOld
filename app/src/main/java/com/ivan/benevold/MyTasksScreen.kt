package com.ivan.benevold

import LoginResponse
import Network
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_my_tasks_screen.*
import kotlinx.android.synthetic.main.task_cell.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException


class MyTasksScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tasks_screen)
        val dataResPrev = intent.extras?.get("data") as LoginResponse


        val back = findViewById<TextView>(R.id.backButtonTasks)
        back.setOnClickListener {
            val intent = Intent(this, ClientMainScreen::class.java)
            intent.putExtra("data", dataResPrev);
            startActivity(intent)
        }


        var annonceArray: MutableList<Task> = ArrayList()

        GlobalScope.launch(Dispatchers.Default) {
            val req = AnnoncesRequest(dataResPrev.user_id.toString())
            try {
                val dataRes = Network.api.annonceSAPICallAsync(req, dataResPrev.token.toString()).await()
                if(dataRes.response != null) {
                    withContext(Dispatchers.Main) {
                        dataRes.response.forEach {
                            annonceArray.add(
                                    Task(
                                            dataResPrev.token,
                                            it._id,
                                            it.title!!,
                                            it.category!!,
                                            it.description,
                                            it.phone,
                                            it.email,
                                            it.contact,
                                            it.address,
                                            it.date,
                                            it.time
                                    )
                            )
                        }
                        //on affiche les elements uns apres l'autres
                        list.layoutManager = LinearLayoutManager(this@MyTasksScreen)
                        list.adapter = TaskAdapter(
                                tasks = annonceArray
                        )
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                }
            }
        }


    }
}

class TaskAdapter(private val tasks: List<Task>) :
    RecyclerView.Adapter<TaskViewHolder>(){


    //comment creer une cellule
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.task_cell, parent, false
                )
        )
    }
    override fun getItemCount(): Int {
       return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.updateItem(
                position = position + 1,
                task = tasks[position]
        )
    }

}

class TaskViewHolder(v: View) : RecyclerView.ViewHolder(v){
    val descriptionTextView: TextView = v.descriptionCell
    val titleTextView: TextView = v.titleCell
    val heureTextView: TextView = v.heureCell
    val dateTextView: TextView = v.dateCell
    var tasksID: String = ""
    var token: String = ""



    init {
        titleTextView.setOnClickListener {
            val intent = Intent(v.context, MoreDetailsScreen::class.java)
            GlobalScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    intent.putExtra("id", tasksID);
                    intent.putExtra("token", token);
                    v.context.startActivity(intent)
                }
            }

        }
    }

    fun updateItem(position: Int, task: Task){
        token = task.token.toString()
        tasksID = task.id.toString()
        if(task.description != "null"){
            descriptionTextView.text = task.description
        }else{
            descriptionTextView.visibility = View.GONE
        }
        if(task.title != "null"){
            titleTextView.text = task.title
        }else{
            titleTextView.visibility = View.GONE
        }
        if(task.time != "null"){
            heureTextView.text = task.time
        }else{
            heureTextView.visibility = View.GONE
        }
        if(task.date != "null"){
            dateTextView.text = task.date
        }else{
            dateTextView.visibility = View.GONE
        }
    }

}
class AnnoncesRequest internal constructor(val user_id: String)