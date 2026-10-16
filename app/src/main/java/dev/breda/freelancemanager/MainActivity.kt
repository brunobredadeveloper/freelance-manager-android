package dev.breda.freelancemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import dev.breda.freelancemanager.database.FinanceDatabase
import dev.breda.freelancemanager.database.RoomFinanceRepository
import dev.breda.freelancemanager.domain.Money
import dev.breda.freelancemanager.domain.ObservePortfolio
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            FinanceDatabase::class.java,
            "freelance-manager.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = RoomFinanceRepository(database.financeDao())
        val factory = PortfolioViewModel.Factory(ObservePortfolio(repository))
        val viewModel = ViewModelProvider(this, factory)[PortfolioViewModel::class.java]
        setContent {
            MaterialTheme {
                DashboardScreen(viewModel)
            }
        }
    }
}

private fun formatBrl(value: Money): String =
    NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))
        .format(java.math.BigDecimal.valueOf(value.cents, 2))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreen(viewModel: PortfolioViewModel) {
    val state by viewModel.state.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text("Freelance Manager") }) }) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Resumo financeiro", style = MaterialTheme.typography.headlineMedium)
            when (val current = state) {
                PortfolioUiState.Loading -> CircularProgressIndicator()
                is PortfolioUiState.Error -> Text(current.message, color = MaterialTheme.colorScheme.error)
                is PortfolioUiState.Ready -> {
                    SummaryCard("Contratado", formatBrl(current.summary.contracted))
                    SummaryCard("Recebido", formatBrl(current.summary.received))
                    SummaryCard("Pendente", formatBrl(current.summary.outstanding))
                    if (current.summary.contracted == Money.ZERO) {
                        Text("Nenhum contrato cadastrado ainda. O cadastro será disponibilizado na próxima etapa.")
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(label: String, amount: String) {
    Card {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(label, style = MaterialTheme.typography.labelLarge)
            Text(amount, style = MaterialTheme.typography.headlineSmall)
        }
    }
}
