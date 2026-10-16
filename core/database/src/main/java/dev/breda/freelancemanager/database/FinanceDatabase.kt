package dev.breda.freelancemanager.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "clients")
data class ClientEntity(
    @PrimaryKey val id: String,
    val name: String,
    val contact: String?
)

@Entity(
    tableName = "projects",
    foreignKeys = [ForeignKey(
        entity = ClientEntity::class,
        parentColumns = ["id"],
        childColumns = ["clientId"],
        onDelete = ForeignKey.RESTRICT
    )],
    indices = [Index("clientId")]
)
data class ProjectEntity(
    @PrimaryKey val id: String,
    val clientId: String,
    val title: String,
    val description: String,
    val status: String
)

@Entity(
    tableName = "contracts",
    foreignKeys = [ForeignKey(
        entity = ProjectEntity::class,
        parentColumns = ["id"],
        childColumns = ["projectId"],
        onDelete = ForeignKey.RESTRICT
    )],
    indices = [Index("projectId")]
)
data class ContractEntity(
    @PrimaryKey val id: String,
    val projectId: String,
    val title: String,
    val amountCents: Long,
    val currency: String = "BRL"
)

@Entity(
    tableName = "installments",
    foreignKeys = [ForeignKey(
        entity = ContractEntity::class,
        parentColumns = ["id"],
        childColumns = ["contractId"],
        onDelete = ForeignKey.RESTRICT
    )],
    indices = [Index("contractId")]
)
data class InstallmentEntity(
    @PrimaryKey val id: String,
    val contractId: String,
    val expectedCents: Long,
    val dueEpochDay: Long
)

@Entity(
    tableName = "receipts",
    foreignKeys = [ForeignKey(
        entity = InstallmentEntity::class,
        parentColumns = ["id"],
        childColumns = ["installmentId"],
        onDelete = ForeignKey.RESTRICT
    )],
    indices = [Index("installmentId")]
)
data class ReceiptEntity(
    @PrimaryKey val id: String,
    val installmentId: String,
    val amountCents: Long,
    val receivedEpochDay: Long,
    val evidenceUri: String?
)

@Dao
interface FinanceDao {
    @Insert suspend fun addClient(value: ClientEntity)
    @Insert suspend fun addProject(value: ProjectEntity)
    @Insert suspend fun addContract(value: ContractEntity)
    @Insert suspend fun addInstallment(value: InstallmentEntity)
    @Insert suspend fun addReceipt(value: ReceiptEntity)

    @Query("SELECT * FROM clients ORDER BY name")
    fun observeClients(): Flow<List<ClientEntity>>

    @Query("SELECT * FROM projects ORDER BY title")
    fun observeProjects(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM contracts ORDER BY title")
    fun observeContracts(): Flow<List<ContractEntity>>

    @Query("SELECT * FROM installments")
    fun observeInstallments(): Flow<List<InstallmentEntity>>

    @Query("SELECT * FROM receipts")
    fun observeReceipts(): Flow<List<ReceiptEntity>>
}

@Database(
    entities = [
        ClientEntity::class,
        ProjectEntity::class,
        ContractEntity::class,
        InstallmentEntity::class,
        ReceiptEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class FinanceDatabase : RoomDatabase() {
    abstract fun financeDao(): FinanceDao
}
