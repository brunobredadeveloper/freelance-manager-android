package dev.breda.freelancemanager.domain

import kotlinx.coroutines.flow.Flow

/** Contracts are defined by the domain, not by Room or Firebase. */
interface ProjectRepository {
    fun observeProjects(): Flow<List<Project>>
    suspend fun create(project: Project)
}

interface FinanceRepository {
    fun observeInstallments(): Flow<List<Installment>>
    fun observeReceipts(): Flow<List<Receipt>>
    suspend fun registerReceipt(id: String, installmentId: String, amount: Money, epochDay: Long)
}
