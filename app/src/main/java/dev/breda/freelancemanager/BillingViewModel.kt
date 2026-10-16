package dev.breda.freelancemanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.breda.freelancemanager.domain.BillingRepository
import dev.breda.freelancemanager.domain.Contract
import dev.breda.freelancemanager.domain.CreateContract
import dev.breda.freelancemanager.domain.CreateInstallment
import dev.breda.freelancemanager.domain.FinanceRepository
import dev.breda.freelancemanager.domain.Money
import dev.breda.freelancemanager.domain.RegisterReceipt
import dev.breda.freelancemanager.domain.ScheduledInstallment
import java.time.LocalDate
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class BillingUiState(
    val contracts: List<Contract> = emptyList(),
    val installments: List<ScheduledInstallment> = emptyList(),
    val saving: Boolean = false,
    val error: String? = null
)

class BillingViewModel(
    private val billing: BillingRepository,
    private val finance: FinanceRepository
) : ViewModel() {
    private val busy = MutableStateFlow(false)
    private val error = MutableStateFlow<String?>(null)
    val state: StateFlow<BillingUiState> = combine(
        billing.observeContracts(), billing.observeScheduledInstallments(), busy, error
    ) { contracts, installments, saving, failure ->
        BillingUiState(contracts, installments, saving, failure)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), BillingUiState())

    fun addContract(projectId: String, title: String, amountCents: Long) = save {
        CreateContract(billing)(
            Contract(UUID.randomUUID().toString(), projectId, title.trim(), Money(amountCents))
        )
    }

    fun addInstallment(contractId: String, amountCents: Long, dueDate: String) = save {
        val day = LocalDate.parse(dueDate).toEpochDay()
        CreateInstallment(billing)(
            ScheduledInstallment(UUID.randomUUID().toString(), contractId, Money(amountCents), day)
        )
    }

    fun addReceipt(installmentId: String, amountCents: Long, receivedDate: String) = save {
        RegisterReceipt(finance)(
            UUID.randomUUID().toString(), installmentId, Money(amountCents),
            LocalDate.parse(receivedDate).toEpochDay()
        )
    }

    private fun save(action: suspend () -> Unit) = viewModelScope.launch {
        busy.value = true
        try {
            action()
            error.value = null
        } catch (_: Exception) {
            error.value = "Não foi possível salvar. Confira valores, datas e vínculos."
        } finally {
            busy.value = false
        }
    }

    class Factory(
        private val billing: BillingRepository,
        private val finance: FinanceRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            BillingViewModel(billing, finance) as T
    }
}
