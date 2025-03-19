package org.example.appdevelopment.ex1.vehicle

class Brand(
    private val name: String,
    private val country: String,
    private val phone: String,
    private val email: String
) {
    fun getName(): String = name
    fun getCountry(): String = country
    fun getPhone(): String = phone
    fun getEmail(): String = email

    override fun toString(): String {
        return "Brand(name='$name', country='$country', phone='$phone', email='$email')"
    }
}