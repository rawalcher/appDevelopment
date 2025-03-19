package org.example.appdevelopment.ex1

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import java.util.concurrent.TimeUnit

class VehicleTest {

    private lateinit var ktm: Brand
    private lateinit var viennaWorkshop: Workshop
    private lateinit var linzWorkshop: Workshop
    private lateinit var testVehicle: Vehicle

    @BeforeEach
    fun setUp() {
        ktm = Brand("KTM X-Bow", "Austria", "+43-7242-69000", "info@ktm.com")

        viennaWorkshop = Workshop(
            "KTM Vienna Service",
            "Austria",
            1010,
            "Vienna",
            "Stephansplatz 1",
            "+43-1-533-87-90"
        )

        linzWorkshop = Workshop(
            "KTM Linz Center",
            "Austria",
            4020,
            "Linz",
            "Hauptplatz 12",
            "+43-732-781-234"
        )

        testVehicle = Vehicle(
            id = 4321,
            name = "KTM X-Bow GT",
            brand = ktm,
            weight = 790,
            maxPermissibleWeight = 1050,
            maxSpeed = 220.0
        )
    }

    @Test
    fun testVehicleAcceleration() {
        val initialSpeed = testVehicle.speed
        val newSpeed = testVehicle.accelerate()

        assertTrue(newSpeed > initialSpeed)
        assertTrue(newSpeed <= 220.0)
    }

    @Test
    fun testVehicleBraking() {
        repeat(5) {
            testVehicle.accelerate()
        }

        val currentSpeed = testVehicle.accelerate()
        val newSpeed = testVehicle.brake()

        assertTrue(newSpeed < currentSpeed)
        assertTrue(newSpeed >= 0.0)
    }

    @Test
    fun testSpeedCannotExceedMaxSpeed() {
        repeat(100) {
            testVehicle.accelerate()
        }

        val finalSpeed = testVehicle.accelerate()
        assertTrue(finalSpeed <= 220.0)
    }

    @Test
    fun testWorkshopManagement() {
        testVehicle.addWorkshop(viennaWorkshop)
        testVehicle.addWorkshop(linzWorkshop)

        val foundWorkshop = testVehicle.getWorkshop(1010)
        assertNotNull(foundWorkshop)
        assertEquals("KTM Vienna Service", foundWorkshop?.getName())

        val nonExistentWorkshop = testVehicle.getWorkshop(9999)
        assertNull(nonExistentWorkshop)
    }

    @Test
    fun testGetNameReturnsCorrectName() {
        assertEquals("KTM X-Bow GT", testVehicle.getName())
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    fun testRaceSimulationCompletesWithinTimeLimit() = runBlocking {
        val raceTime = testVehicle.race(1)
        assertTrue(raceTime > 0.0)
    }

    @Test
    fun testDrive() {
        // there is no getter if we set the variable to public getter

        assertEquals(0.0, testVehicle.distance)

        testVehicle.drive(10)

        assertEquals(10.0, testVehicle.distance)
    }
}