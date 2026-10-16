package dev.breda.freelancemanager.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/** Reactive use case; emits whenever installments or receipts change. */
class ObservePortfolio(private val repository: FinanceRepository) {
    operator fun invoke(): Flow<PortfolioSummary> =
        combine(repository.observeInstallments(), repository.observeReceipts()) { installments, receipts ->
            summarizePortfolio(installments, receipts)
        }
}
