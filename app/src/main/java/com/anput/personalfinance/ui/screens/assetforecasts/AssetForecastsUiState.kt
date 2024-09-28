package com.anput.personalfinance.ui.screens.assetforecasts

import com.anput.personalfinance.data.models.MonthlyReport

data class AssetForecastsUiState (
    val years: List<Int> = listOf(),
    val monthlyReports: List<MonthlyReport> = listOf()
)