package dev.breda.freelancemanager.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MoneyTest {
    @Test fun partialReceipts() {
        val installment = Installment("a", Money(50_000))
        assertEquals(Money(20_000), outstanding(installment, listOf(Receipt("a", Money(30_000)))))
    }

    @Test fun unrelatedReceiptsDoNotCount() {
        assertEquals(Money(10_000), outstanding(Installment("a", Money(10_000)), listOf(Receipt("b", Money(9_000)))))
    }

    @Test fun overpaymentIsVisible() {
        assertEquals(Money(-10_000), outstanding(Installment("a", Money(50_000)), listOf(Receipt("a", Money(60_000)))))
    }

    @Test fun rejectsNegativeReceipt() {
        assertFailsWith<IllegalArgumentException> {
            outstanding(Installment("a", Money(100)), listOf(Receipt("a", Money(-1))))
        }
    }
}
