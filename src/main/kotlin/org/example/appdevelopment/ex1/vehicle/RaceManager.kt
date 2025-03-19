package org.example.appdevelopment.ex1.vehicle

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class RaceManager {
    suspend fun conductRace(vehicles: List<Vehicle>, distance: Int) {
        println("Racing for a Distance of : $distance km")
        println("Participating Racists: ${vehicles.map { it.getName() }.joinToString(", ")}")

        val raceResults = mutableMapOf<String, Double>()

        coroutineScope {
            val jobs = vehicles.map { vehicle ->
                async {
                    val time = vehicle.race(distance)
                    raceResults[vehicle.getName()] = time
                    time
                }
            }

            jobs.forEach { it.await() }
        }

        val sortedResults = raceResults.entries.sortedBy { it.value }

        sortedResults.forEachIndexed { index, (vehicleName, time) ->
            val position = index + 1
            val medal = when (position) {
                1 -> "\u001B[43;30m GOLD \u001B[0m"
                2 -> "\u001B[47;30m SILVER \u001B[0m"
                3 -> "\u001B[41;30m BRONZE \u001B[0m"
                else -> "       "
            }
            println("$medal #$position: $vehicleName - ${String.format("%.2f", time)} seconds")
        }
    }
}