package dev.breda.freelancemanager.database

import dev.breda.freelancemanager.domain.BillingRepository
import dev.breda.freelancemanager.domain.Contract
import dev.breda.freelancemanager.domain.Money
import dev.breda.freelancemanager.domain.ScheduledInstallment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomBillingRepository(private val dao: FinanceDao) : BillingRepository {
    override fun observeContracts(): Flow<List<Contract>> =
        dao.observeContracts().map { values ->
            values.map { Contract(it.id, it.projectId, it.title, Money(it.amountCents)) }
        }

    override fun observeScheduledInstallments(): Flow<List<ScheduledInstallment>> =
        dao.observeInstallments().map { values ->
            values.map { ScheduledInstallment(it.id, it.contractId, Money(it.expectedCents), it.dueEpochDay) }
        }

    override suspend fun createContract(contract: Contract) {
        dao.addContract(ContractEntity(contract.id, contract.projectId, contract.title, contract.amount.cents))
    }

    override suspend fun createInstallment(installment: ScheduledInstallment) {
        dao.addInstallment(
            InstallmentEntity(installment.id, installment.contractId, installment.expected.cents, installment.dueEpochDay)
        )
    }
}
