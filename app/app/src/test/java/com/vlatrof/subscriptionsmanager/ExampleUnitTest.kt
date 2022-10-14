package com.vlatrof.subscriptionsmanager

import org.junit.Test
import java.time.LocalDate

class ExampleUnitTest {

    @Test
    fun simpleTest() {

        val list = listOf(
            LocalDate.of(24,10,1),
            LocalDate.of(23,10,1),
            LocalDate.of(25,10,1),
        )

        println(list.sortedBy{it}.toString())
        println(list.sortedByDescending{it}.toString())

    }

}