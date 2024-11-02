package me.looma.ui.screens.monthlyreport

data class MonthlyReportUiState (
    val actualBalance: Int = 0,
    val plannedBalance: Int = 0,
    val actualSaving: Int = 0,
    val plannedSaving: Int = 0,
    val incomes: Int = 0,
    val actualOutcomes: Int = 0,
    val plannedOutcomes: Int = 0,
    val actualInvestments: Int = 0,
    val plannedInvestments: Int = 0,
    val actualOtherBalances: Int = 0,
    val plannedOtherBalances: Int = 0
)