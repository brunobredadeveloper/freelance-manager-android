package dev.breda.freelancemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import dev.breda.freelancemanager.database.FinanceDatabase
import dev.breda.freelancemanager.database.RoomClientRepository
import dev.breda.freelancemanager.database.RoomFinanceRepository
import dev.breda.freelancemanager.database.RoomProjectRepository
import dev.breda.freelancemanager.domain.Money
import dev.breda.freelancemanager.domain.ObservePortfolio
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(applicationContext, FinanceDatabase::class.java, "freelance-manager.db")
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = database.financeDao()
        val portfolio = ViewModelProvider(
            this,
            PortfolioViewModel.Factory(ObservePortfolio(RoomFinanceRepository(dao)))
        )[PortfolioViewModel::class.java]
        val workspace = ViewModelProvider(
            this,
            WorkspaceViewModel.Factory(RoomClientRepository(dao), RoomProjectRepository(dao))
        )[WorkspaceViewModel::class.java]
        setContent {
            MaterialTheme {
                HomeScreen(portfolio, workspace)
            }
        }
    }
}

private fun formatBrl(value: Money): String =
    NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))
        .format(BigDecimal.valueOf(value.cents, 2))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(portfolio: PortfolioViewModel, workspace: WorkspaceViewModel) {
    val finance by portfolio.state.collectAsState()
    val work by workspace.state.collectAsState()
    var clientName by rememberSaveable { mutableStateOf("") }
    var projectName by rememberSaveable { mutableStateOf("") }
    var selectedClientId by rememberSaveable { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Freelance Manager") }) }) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Visão geral", style = MaterialTheme.typography.headlineMedium)
            when (val current = finance) {
                PortfolioUiState.Loading -> CircularProgressIndicator()
                is PortfolioUiState.Error ->
                    Text(current.message, color = MaterialTheme.colorScheme.error)
                is PortfolioUiState.Ready -> {
                    SummaryCard("Contratado", formatBrl(current.summary.contracted))
                    SummaryCard("Recebido", formatBrl(current.summary.received))
                    SummaryCard("Pendente", formatBrl(current.summary.outstanding))
                    if (current.summary.contracted == Money.ZERO) {
                        Text("Sem parcelas registradas. O módulo de contratos está em desenvolvimento.")
                    }
                }
            }

            Text("Clientes", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(
                value = clientName,
                onValueChange = { clientName = it },
                label = { Text("Nome do cliente") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                enabled = !work.saving && clientName.isNotBlank(),
                onClick = {
                    workspace.addClient(clientName)
                    clientName = ""
                }
            ) { Text("Adicionar cliente") }

            work.clients.forEach { client ->
                val chosen = selectedClientId == client.id
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { selectedClientId = client.id }
                ) {
                    Row(Modifier.padding(16.dp)) {
                        Text(if (chosen) "✓ " else "○ ")
                        Text(client.name)
                    }
                }
            }

            Text("Projetos", style = MaterialTheme.typography.titleLarge)
            Text("Selecione um cliente acima para vincular um novo projeto.")
            OutlinedTextField(
                value = projectName,
                onValueChange = { projectName = it },
                label = { Text("Nome do projeto") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                enabled = !work.saving && selectedClientId.isNotBlank() && projectName.isNotBlank(),
                onClick = {
                    workspace.addProject(selectedClientId, projectName)
                    projectName = ""
                }
            ) { Text("Adicionar projeto") }
            work.projects.forEach { project ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(project.title, style = MaterialTheme.typography.titleMedium)
                        Text("Status: ${project.status.name}")
                    }
                }
            }
            work.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Text(
                "Dados armazenados localmente. Contratos, parcelas e anexos serão habilitados nas próximas etapas.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun SummaryCard(label: String, amount: String) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(label, style = MaterialTheme.typography.labelLarge)
            Text(amount, style = MaterialTheme.typography.headlineSmall)
        }
    }
}
