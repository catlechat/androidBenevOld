package com.ivan.benevold

data class Task(
        val title: String,
        val category: String,
        val description: String? = null,
        val phone: String? = null,
        val email: String? = null,
        val contact: String? = null,
        val address: String? = null,
        val date: String? = null,
        val time: String? = null, ) : java.io.Serializable {





    override fun toString(): String {
        return "Task(title='$title', category='$category'," +
                " description=$description, phone=$phone, " +
                "email=$email, contact=$contact, address=$address, " +
                "date=$date, time=$time)"
    }
}