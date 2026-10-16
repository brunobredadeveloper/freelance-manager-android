package dev.breda.freelancemanager.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UseCasesTest {
    private class FakeFinanceRepository : FinanceRepository {
        val installments = MutableStateFlow(listOf(Installment("first", Money(10_000))))
        val receipts = MutableStateFlow<List<Receipt>>(emptyList())
        override fun observeInstallments(): Flow<List<Installment>> = installments
        override fun observeReceipts(): Flow<List<Receipt>> = receipts
        override suspend fun registerReceipt(id: String, installmentId: String, amount: Money, epochDay: Long) {
            receipts.value = receipts.value + Receipt(installmentId, amount)
        }
    }

    @Test fun registerReceiptUpdatesReactiveSummary() = runBlocking {
        val repository = FakeFinanceRepository()
        val observe = ObservePortfolio(repository)
        assertEquals(Money(10_000), observe().first().outstanding)
        RegisterReceipt(repository)("receipt-1", "first", Money(4_000), 20_000L)
        assertEquals(Money(6_000), observe().first().outstanding)
    }

    @Test fun invalidReceiptIsRejected() = runBlocking {
        val repository = FakeFinanceRepository()
        assertFailsWith<IllegalArgumentException> {
            RegisterReceipt(repository)("", "first", Money(100), 20_000L)
        }
        assertEquals(0, repository.receipts.value.size)
    }
}
