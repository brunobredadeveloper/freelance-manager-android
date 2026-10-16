package dev.breda.freelancemanager.database

import dev.breda.freelancemanager.domain.Client
import dev.breda.freelancemanager.domain.ClientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomClientRepository(private val dao: FinanceDao) : ClientRepository {
    override fun observeClients(): Flow<List<Client>> =
        dao.observeClients().map { list -> list.map { Client(it.id, it.name, it.contact) } }

    override suspend fun create(client: Client) {
        dao.addClient(ClientEntity(client.id, client.name, client.contact))
    }
}
