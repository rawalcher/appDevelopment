package org.example.appdevelopment.ex1.vehicle

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import java.util.concurrent.TimeUnit

class RaceManagerTest {

    private lateinit var raceManager: RaceManager
    private lateinit var vehicles: List<Vehicle>
    private lateinit var ktm: Brand
    private lateinit var bmw: Brand

    @BeforeEach
    fun setUp() {
        raceManager = RaceManager()

        ktm = Brand("KTM X-Bow", "Austria", "+43-7242-69000", "info@ktm.com")
        bmw = Brand("BMW", "Germany", "+49-89-1250-16000", "kundenbetreuung@bmw.at")

        vehicles = listOf(
            Vehicle(
                id = 1,
                name = "KTM X-Bow GT",
                brand = ktm,
                weight = 790,
                maxPermissibleWeight = 1050,
                maxSpeed = 220.0
            ),
            Vehicle(
                id = 2,
                name = "BMW 3 Series",
                brand = bmw,
                weight = 1580,
                maxPermissibleWeight = 2100,
                maxSpeed = 180.0
            )
        )
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    fun testRaceCompletesWithMultipleVehicles() = runBlocking {
        raceManager.conductRace(vehicles, 2)
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    fun testRaceWithSingleVehicle() = runBlocking {
        raceManager.conductRace(listOf(vehicles.first()), 1)
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    fun testRaceWithEmptyVehicleList() = runBlocking {
        raceManager.conductRace(emptyList(), 1)
    }
}