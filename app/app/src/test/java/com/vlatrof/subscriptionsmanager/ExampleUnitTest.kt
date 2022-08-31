package com.vlatrof.subscriptionsmanager

import com.vlatrof.subscriptionsmanager.domain.models.Subscription
import org.junit.Test
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

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

        val s = Subscription(
            title = "Yandex Plus",
            startDate = LocalDate.of(2022, 8, 15),
            renewalPeriod = Period.ofMonths(1),
            paymentCost = 249,
            paymentCurrency = Currency.getInstance("RUB")
        )

        println(s.toString())

    }
}