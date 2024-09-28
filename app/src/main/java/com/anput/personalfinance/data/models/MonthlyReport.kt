package com.anput.personalfinance.data.models

data class MonthlyReport (
    var month: String = "",
    var actualBalance: Int = 0,
    var plannedBalance: Int = 0,
)