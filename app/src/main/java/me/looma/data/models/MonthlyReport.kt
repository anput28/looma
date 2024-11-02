package me.looma.data.models

data class MonthlyReport (
    var month: Int = 0,
    var actualBalance: Int = 0,
    var plannedBalance: Int = 0,
    var actualSaving: Int = 0,
    var plannedSaving: Int = 0,
    var incomes: ReportItem = ReportItem(),
    var actualOutcomes: ReportItem = ReportItem(),
    var plannedOutcomes: ReportItem = ReportItem(),
    var actualInvestments: ReportItem = ReportItem(),
    var plannedInvestments: ReportItem = ReportItem(),
    var actualOtherBalances: ReportItem = ReportItem(),
    var plannedOtherBalances: ReportItem = ReportItem()
)

data class ReportItem (
    var total: Int = 0,
    var fullTotal: Int? = null
)
