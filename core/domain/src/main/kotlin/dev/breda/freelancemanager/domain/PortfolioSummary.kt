package dev.breda.freelancemanager.domain

data class PortfolioSummary(
    val contracted: Money,
    val received: Money,
    val outstanding: Money
)

/** Use receipt transactions, not an installment's paid flag, as the source of truth. */
fun summarizePortfolio(installments: List<Installment>, receipts: List<Receipt>): PortfolioSummary {
    val knownIds = installments.map { it.id }.toSet()
    require(knownIds.size == installments.size) { "Duplicate installment ids" }
    require(receipts.all { it.installmentId in knownIds }) { "Orphan receipt" }
    val contracted = installments.fold(Money.ZERO) { sum, item ->
        require(item.expected.cents >= 0)
        sum + item.expected
    }
    val received = receipts.fold(Money.ZERO) { sum, item ->
        require(item.amount.cents > 0)
        sum + item.amount
    }
    return PortfolioSummary(contracted, received, contracted - received)
}
