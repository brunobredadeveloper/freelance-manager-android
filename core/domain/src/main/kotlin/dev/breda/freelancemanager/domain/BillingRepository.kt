package dev.breda.freelancemanager.domain

import kotlinx.coroutines.flow.Flow

data class ScheduledInstallment(
    val id: String,
    val contractId: String,
    val expected: Money,
    val dueEpochDay: Long
)

interface BillingRepository {
    fun observeContracts(): Flow<List<Contract>>
    fun observeScheduledInstallments(): Flow<List<ScheduledInstallment>>
    suspend fun createContract(contract: Contract)
    suspend fun createInstallment(installment: ScheduledInstallment)
}

class CreateContract(private val repository: BillingRepository) {
    suspend operator fun invoke(contract: Contract) {
        require(contract.amount.cents > 0) { "Contract amount must be positive" }
        repository.createContract(contract)
    }
}

class CreateInstallment(private val repository: BillingRepository) {
    suspend operator fun invoke(installment: ScheduledInstallment) {
        require(installment.expected.cents > 0) { "Installment amount must be positive" }
        repository.createInstallment(installment)
    }
}
