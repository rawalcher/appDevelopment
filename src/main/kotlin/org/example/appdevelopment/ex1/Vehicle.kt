package org.example.appdevelopment.ex1

import kotlinx.coroutines.*
import kotlin.random.Random

class Vehicle(
    private val id: Int,
    private val name: String,
    private val brand: Brand,
    private var weight: Int,
    private val maxPermissibleWeight: Int,
    private val maxSpeed: Double = 200.0
) {

    // var is public meaning get is public
    // but set is private, nice
    var speed: Double = 0.0
        private set(value) {
            field = value.coerceIn(0.0, maxSpeed)
        }

    var distance: Double = 0.0
        private set

    private val workshops = ArrayList<Workshop>()

    fun addWorkshop(workshop: Workshop) {
        workshops.add(workshop)
    }

    fun accelerate(): Double {
        val increase = Random.nextDouble(10.0, 50.0)
        this.speed += increase
        return this.speed
    }

    fun brake(): Double {
        val decrease = Random.nextDouble(10.0, 50.0)
        this.speed -= decrease
        return this.speed
    }

    fun drive(kilometers: Int) {
        println("$name starts driving $kilometers kilometers...")
        distance = 0.0
        speed = 0.0

        for (km in 1..kilometers) {
            println("Kilometer $km:")
            val actions = (3..5).random()

            for (action in 1..actions) {
                val shouldAccelerate = (0..1).random() == 1

                if (shouldAccelerate) {
                    val newSpeed = accelerate()
                    println("  Accelerating to %.2f km/h".format(newSpeed))
                } else {
                    val newSpeed = brake()
                    println("  Braking to %.2f km/h".format(newSpeed))
                }
            }
            // we drive 1 km
            distance += 1.0
        }

        println("$name finished driving $kilometers kilometers. Final speed: %.2f km/h".format(speed))
    }

    suspend fun race(raceDistance: Int): Double {
        println("$name starts racing!")
        distance = 0.0
        speed = 0.0

        val startTime = System.currentTimeMillis()

        while (distance < raceDistance) {
            if (Random.nextDouble() > 0.3) {
                speed = accelerate()
                println("$name accelerates to %.1f km/h".format(speed))
            } else {
                speed = brake()
                println("$name brakes to %.1f km/h".format(speed))
            }

            val distanceCovered = speed / 360.0
            distance += distanceCovered

            println("$name has covered %.2f km of $raceDistance km".format(distance))

            delay(200)
        }

        val raceTimeSeconds = (System.currentTimeMillis() - startTime) / 1000.0
        println("$name has finished the race! Time: %.2f seconds".format(raceTimeSeconds))

        return raceTimeSeconds
    }

    fun printInfo() {
        println("Vehicle Information")
        println("ID: $id")
        println("Name: $name")
        println("Brand: ${brand.getName()} (${brand.getCountry()})")
        println("Brand Contact: ${brand.getPhone()}, ${brand.getEmail()}")
        println("Weight: $weight kg")
        println("Max Permissible Weight: $maxPermissibleWeight kg")
        println("Current Speed: $speed km/h")
        println("Max Speed: $maxSpeed km/h")

        println("Authorized Workshops:")
        if (workshops.isEmpty()) {
            println("  No authorized workshops")
        } else {
            workshops.forEachIndexed { index, workshop ->
                println("  ${index + 1}. ${workshop.getName()}, ${workshop.getCity()}, ${workshop.getCountry()}")
                println("     Address: ${workshop.getStreet()}, ${workshop.getPostcode()}")
                println("     Phone: ${workshop.getPhone()}")
            }
        }
    }

    fun getWorkshop(postcode: Int): Workshop? {
        return workshops.find { it.getPostcode() == postcode }
    }

    fun getName(): String = name
}

fun main() = runBlocking {
    println("Starting Vehicle Racing Demo")

    val workshop = Workshop("Test Workshop", "Test Country", 9999, "Test City", "Test Street", "+123456789")

    val brand = Brand("Test Brand", "Test Country", "+123456789", "test@example.com")
    val vehicle = Vehicle(
        id = 1,
        name = "Test Vehicle",
        brand = brand,
        weight = 1000,
        maxPermissibleWeight = 1500,
        maxSpeed = 200.0
    )

    vehicle.printInfo()
    vehicle.addWorkshop(workshop)
    vehicle.printInfo()

    val raceManager = RaceManager()
    raceManager.conductRace(listOf(vehicle), 1)

    vehicle.drive(5)
}