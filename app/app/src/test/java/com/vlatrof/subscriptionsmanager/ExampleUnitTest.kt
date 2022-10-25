package com.vlatrof.subscriptionsmanager

import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class ExampleUnitTest {

    @Test
    fun simpleTest() {

        val currentTime = LocalTime.of(13, 0).toSecondOfDay()
        val alertTime = LocalTime.of(12, 0).toSecondOfDay()

        val initialDelay = if (currentTime < alertTime) {
            alertTime - currentTime
        } else {
            86400 - currentTime + alertTime
        }

        println(initialDelay)

    }

}