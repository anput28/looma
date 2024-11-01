package me.looma

interface Destination {
    val route: String
}

object AssetForecasts : Destination {
    override val route = "asset-forecasts"
}

object MonthlyReport : Destination {
    override val route = "monthly-report"
}

object Incomes : Destination {
    override val route = "incomes"
}

object Investments : Destination {
    override val route = "investments"
}

object Expenditures : Destination {
    override val route = "expenditures"
}