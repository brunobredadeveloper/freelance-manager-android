package dev.breda.freelancemanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.breda.freelancemanager.domain.ObservePortfolio
import dev.breda.freelancemanager.domain.PortfolioSummary
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface PortfolioUiState {
    data object Loading : PortfolioUiState
    data class Ready(val summary: PortfolioSummary) : PortfolioUiState
    data class Error(val message: String) : PortfolioUiState
}

class PortfolioViewModel(observePortfolio: ObservePortfolio) : ViewModel() {
    val state: StateFlow<PortfolioUiState> = observePortfolio()
        .map<PortfolioSummary, PortfolioUiState> { PortfolioUiState.Ready(it) }
        .catch { emit(PortfolioUiState.Error("Não foi possível carregar o resumo financeiro.")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PortfolioUiState.Loading)

    class Factory(private val useCase: ObservePortfolio) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            PortfolioViewModel(useCase) as T
    }
}
