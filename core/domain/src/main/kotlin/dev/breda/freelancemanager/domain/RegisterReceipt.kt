package dev.breda.freelancemanager.domain

/** Validates receipt input before crossing the persistence boundary. */
class RegisterReceipt(private val repository: FinanceRepository) {
    suspend operator fun invoke(id: String, installmentId: String, amount: Money, epochDay: Long) {
        require(id.isNotBlank()) { "Receipt id is required" }
        require(installmentId.isNotBlank()) { "Installment id is required" }
        require(amount.cents > 0) { "Receipt amount must be positive" }
        repository.registerReceipt(id, installmentId, amount, epochDay)
    }
}
