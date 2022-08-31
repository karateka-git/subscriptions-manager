package com.vlatrof.subscriptionsmanager

import org.junit.Test
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale

class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {

        val startDate = LocalDate.of(2022, 8, 15)
        val periodToAdd = Period.ofMonths(1)
        val resultDate = startDate.plus(periodToAdd)

        val dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru"))

        println()
        println()
        println(dtf.format(resultDate))
        println()
        println()

    }
}