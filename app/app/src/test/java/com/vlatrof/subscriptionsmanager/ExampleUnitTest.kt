package com.vlatrof.subscriptionsmanager

import org.junit.Test
import java.time.LocalDate
import java.time.Period
import java.util.*

class ExampleUnitTest {

    @Test
    fun simpleTest() {

        val ld: LocalDate = LocalDate.parse("2010-07-20") // using default formatter ISO
        val lds: String = ld.toString()

        println()
        println(ld)
        println(lds)
        println()

        val p: Period = Period.parse("P1M")
        val ps: String = p.toString()

        println()
        println(p)
        println(ps)
        println()

        val currency = Currency.getAvailableCurrencies()

        println(currency)

    }

}