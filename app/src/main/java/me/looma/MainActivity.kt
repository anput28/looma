package me.looma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.looma.ui.screens.assetforecasts.AssetForecastsScreen
import me.looma.ui.screens.expenditures.ExpendituresScreen
import me.looma.ui.screens.incomes.IncomesScreen
import me.looma.ui.screens.investments.InvestmentsScreen
import me.looma.ui.screens.monthlyreport.MonthlyReportScreen
import me.looma.ui.theme.PersonalFinanceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PersonalFinanceTheme {
                PersonalFinanceApp()
            }
        }
    }
}

@Composable
fun PersonalFinanceApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AssetForecasts.route) {
        composable(route = AssetForecasts.route) { AssetForecastsScreen(onClick = { navController.navigate(MonthlyReport.route) }) }
        composable(route = MonthlyReport.route) { MonthlyReportScreen() }
        composable(route = Incomes.route) { IncomesScreen() }
        composable(route = Investments.route) { InvestmentsScreen() }
        composable(route = Expenditures.route) { ExpendituresScreen() }
    }
}