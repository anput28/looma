package me.looma.ui.screens.assetforecasts

import me.looma.data.models.MonthlyReport
import me.looma.ui.DataState

data class AssetForecastsUiState (
    val years: List<Int> = listOf(),
    val monthlyReportsState:  DataState<List<MonthlyReport>> = DataState.Loading
)