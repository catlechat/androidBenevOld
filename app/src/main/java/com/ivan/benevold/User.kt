package com.ivan.benevold

data class User(
        val token: String,
        val id: String,
        val firstName: String,
        val lastName: String,
        val phone: String,
        val email: String,
        val address: String,
        val photoLink : String
) {

}