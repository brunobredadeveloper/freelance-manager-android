package dev.breda.freelancemanager.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PortfolioSummaryTest {
    @Test fun summarizesPartialAndFullReceipts() {
        val result = summarizePortfolio(
            listOf(Installment("one", Money(150_000)), Installment("two", Money(660_000))),
            listOf(Receipt("one", Money(150_000)), Receipt("two", Money(25_000)), Receipt("two", Money(60_000)))
        )
        assertEquals(Money(810_000), result.contracted)
        assertEquals(Money(235_000), result.received)
        assertEquals(Money(575_000), result.outstanding)
    }

    @Test fun rejectsOrphanReceipts() {
        assertFailsWith<IllegalArgumentException> {
            summarizePortfolio(emptyList(), listOf(Receipt("missing", Money(100))))
        }
    }

    @Test fun rejectsDuplicateInstallments() {
        assertFailsWith<IllegalArgumentException> {
            summarizePortfolio(listOf(Installment("same", Money(100)), Installment("same", Money(200))), emptyList())
        }
    }
}
