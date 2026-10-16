package dev.breda.freelancemanager.domain

import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    fun observeClients(): Flow<List<Client>>
    suspend fun create(client: Client)
}

class CreateClient(private val repository: ClientRepository) {
    suspend operator fun invoke(client: Client) {
        require(client.name.isNotBlank())
        repository.create(client)
    }
}

class CreateProject(private val repository: ProjectRepository) {
    suspend operator fun invoke(project: Project) {
        require(project.title.isNotBlank())
        repository.create(project)
    }
}
