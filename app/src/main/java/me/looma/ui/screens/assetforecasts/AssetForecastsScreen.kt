package me.looma.ui.screens.assetforecasts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import me.looma.R
import me.looma.data.models.MonthlyReport
import me.looma.data.utils.getMonthName
import me.looma.ui.DataState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetForecastsScreen(
    onClick: () -> Unit,
    assetForecastsViewModel: AssetForecastsViewModel = hiltViewModel()
) {
    val uiState by assetForecastsViewModel.uiStateFlow.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        assetForecastsViewModel.getAllMonthlyReports(uiState.years[selectedTabIndex])
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.asset_forecasts_title)) })
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                ScrollableTabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = selectedTabIndex
                ) {
                    uiState.years.forEachIndexed { index, year  ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = {
                                if(selectedTabIndex != index) {
                                    selectedTabIndex = index
                                    assetForecastsViewModel.getAllMonthlyReports(uiState.years[selectedTabIndex])
                                }
                            },
                            text = { Text("$year") }
                        )
                    }
                }

                when (val state = uiState.monthlyReportsState) {
                    is DataState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))

                    is DataState.Error -> NoData()

                    is DataState.Loaded -> {
                        if(state.data.isEmpty()) {
                            NoData()
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize().padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(state.data) { monthlyReport ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(text = getMonthName(monthlyReport.month))
                                                Button(onClick = {}) { Text(stringResource(R.string.expand)) }
                                            }
                                            Text(
                                                text = "${monthlyReport.actualBalance}",
                                                fontSize = 18.em,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text("${monthlyReport.plannedBalance}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun NoData() {
    Column(
        modifier=Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(R.drawable.no_data),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.no_data))
    }
}