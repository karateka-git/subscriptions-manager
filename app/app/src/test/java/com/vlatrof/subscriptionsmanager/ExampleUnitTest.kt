package com.vlatrof.subscriptionsmanager

import com.vlatrof.subscriptionsmanager.presentation.utils.round
import org.junit.Test
import java.time.LocalDate
import java.time.Period

class ExampleUnitTest {

    @Test
    fun simpleTest() {

        val date = LocalDate.now()

        val period = Period.parse("P1M-1D")

        println(period.addTo(date))

    }







}