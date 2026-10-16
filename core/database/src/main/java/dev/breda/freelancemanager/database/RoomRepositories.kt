package dev.breda.freelancemanager.database

import dev.breda.freelancemanager.domain.FinanceRepository
import dev.breda.freelancemanager.domain.Installment
import dev.breda.freelancemanager.domain.Money
import dev.breda.freelancemanager.domain.Project
import dev.breda.freelancemanager.domain.ProjectRepository
import dev.breda.freelancemanager.domain.ProjectStatus
import dev.breda.freelancemanager.domain.Receipt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomProjectRepository(private val dao: FinanceDao) : ProjectRepository {
    override fun observeProjects(): Flow<List<Project>> = dao.observeProjects().map { entities ->
        entities.map { Project(it.id, it.clientId, it.title, it.description, ProjectStatus.valueOf(it.status)) }
    }

    override suspend fun create(project: Project) {
        dao.addProject(ProjectEntity(project.id, project.clientId, project.title, project.description, project.status.name))
    }
}

class RoomFinanceRepository(private val dao: FinanceDao) : FinanceRepository {
    override fun observeInstallments(): Flow<List<Installment>> =
        dao.observeInstallments().map { entities ->
            entities.map { Installment(it.id, Money(it.expectedCents)) }
        }

    override fun observeReceipts(): Flow<List<Receipt>> =
        dao.observeReceipts().map { entities ->
            entities.map { Receipt(it.installmentId, Money(it.amountCents)) }
        }

    override suspend fun registerReceipt(id: String, installmentId: String, amount: Money, epochDay: Long) {
        require(amount.cents > 0)
        dao.addReceipt(ReceiptEntity(id, installmentId, amount.cents, epochDay, null))
    }
}
