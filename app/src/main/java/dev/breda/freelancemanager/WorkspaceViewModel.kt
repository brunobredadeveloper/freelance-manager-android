package dev.breda.freelancemanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.breda.freelancemanager.domain.Client
import dev.breda.freelancemanager.domain.ClientRepository
import dev.breda.freelancemanager.domain.CreateClient
import dev.breda.freelancemanager.domain.CreateProject
import dev.breda.freelancemanager.domain.Project
import dev.breda.freelancemanager.domain.ProjectRepository
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class WorkspaceUiState(
    val clients: List<Client> = emptyList(),
    val projects: List<Project> = emptyList(),
    val saving: Boolean = false,
    val error: String? = null
)

class WorkspaceViewModel(
    private val clientRepository: ClientRepository,
    private val projectRepository: ProjectRepository
) : ViewModel() {
    private val saving = MutableStateFlow(false)
    private val error = MutableStateFlow<String?>(null)

    val state: StateFlow<WorkspaceUiState> = combine(
        clientRepository.observeClients(),
        projectRepository.observeProjects(),
        saving,
        error
    ) { clients, projects, busy, failure ->
        WorkspaceUiState(clients, projects, busy, failure)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), WorkspaceUiState())

    fun addClient(name: String) = viewModelScope.launch {
        if (name.isBlank()) {
            error.value = "Informe o nome do cliente."
            return@launch
        }
        saving.value = true
        try {
            CreateClient(clientRepository)(Client(UUID.randomUUID().toString(), name.trim()))
            error.value = null
        } catch (_: Exception) {
            error.value = "Não foi possível salvar o cliente."
        } finally {
            saving.value = false
        }
    }

    fun addProject(clientId: String, title: String) = viewModelScope.launch {
        if (clientId.isBlank() || title.isBlank()) {
            error.value = "Selecione um cliente e informe o nome do projeto."
            return@launch
        }
        saving.value = true
        try {
            CreateProject(projectRepository)(
                Project(UUID.randomUUID().toString(), clientId, title.trim())
            )
            error.value = null
        } catch (_: Exception) {
            error.value = "Não foi possível salvar o projeto."
        } finally {
            saving.value = false
        }
    }

    class Factory(
        private val clients: ClientRepository,
        private val projects: ProjectRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            WorkspaceViewModel(clients, projects) as T
    }
}
