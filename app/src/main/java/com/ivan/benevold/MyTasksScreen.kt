package com.ivan.benevold

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

class MyTasksScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tasks_screen)

        //on affiche les elements uns apres l'autres
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = TaskAdapter(
                tasks = listOf(Task("ivan", "etudiant"),Task("Edouard", "prof") )
        )
    }
}

class TaskAdapter(private val tasks : List<Task>) :
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
    val nameTextView: TextView = v.name
    val titleTextView: TextView = v.title

    fun updateItem(position: Int, task: Task){
        nameTextView.text = task.title
        titleTextView.text = task.category

    }

}