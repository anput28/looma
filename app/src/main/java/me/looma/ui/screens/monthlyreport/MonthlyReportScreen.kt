package me.looma.ui.screens.monthlyreport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import me.looma.R
import me.looma.data.utils.getMonthName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlyReportScreen(
    year: Int,
    month: Int,
    onNavigationIconClick: () -> Unit,
    monthlyReportViewModel: MonthlyReportViewModel = hiltViewModel()
) {
    val uiState by monthlyReportViewModel.uiStateFlow.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(start = 16.dp),
                navigationIcon = {
                    IconButton(onClick = onNavigationIconClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                title = { Text("${getMonthName(month)} $year") }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = stringResource(R.string.asset))
                            Text(
                                text = "${uiState.actualBalance}",
                                fontSize = 18.em,
                                fontWeight = FontWeight.Bold
                            )
                            Text("${uiState.plannedBalance}")
                        }
                    }

                    Card {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = stringResource(R.string.saving))
                            Text(
                                text = "${uiState.actualSaving}",
                                fontSize = 18.em,
                                fontWeight = FontWeight.Bold
                            )
                            Text("${uiState.plannedSaving}")
                        }
                    }
                }
            }
        }
    )
}