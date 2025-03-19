package org.example.appdevelopment.ex1.vehicle

class Workshop(
    private val name: String,
    private val country: String,
    private val postcode: Int,
    private val city: String,
    private val street: String,
    private val phone: String
) {
    fun getName(): String = name
    fun getCountry(): String = country
    fun getPostcode(): Int = postcode
    fun getCity(): String = city
    fun getStreet(): String = street
    fun getPhone(): String = phone

    override fun toString(): String {
        return "Workshop(name='$name', country='$country', postcode=$postcode, " + "city='$city', street='$street', phone='$phone')"
    }
}