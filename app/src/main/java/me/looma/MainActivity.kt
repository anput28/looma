package me.looma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.looma.ui.screens.assetforecasts.AssetForecastsScreen
import me.looma.ui.screens.expenditures.ExpendituresScreen
import me.looma.ui.screens.incomes.IncomesScreen
import me.looma.ui.screens.investments.InvestmentsScreen
import me.looma.ui.screens.monthlyreport.MonthlyReportScreen
import me.looma.ui.theme.LoomaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoomaTheme {
                LoomaApp()
            }
        }
    }
}

@Composable
fun LoomaApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AssetForecasts.route) {
        composable(
            route = AssetForecasts.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            AssetForecastsScreen(onClick = { year: Int, month: Int ->
                navController.navigate("${MonthlyReport.route}/$year/$month")
            })
        }
        composable(
            route = "${MonthlyReport.route}/{year}/{month}",
            arguments = listOf(
                navArgument("year") { type = NavType.IntType },
                navArgument("month") { type = NavType.IntType }
            ),
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutHorizontally() }
        ) { backStackEntry ->
            val year =  backStackEntry.arguments!!.getInt("year")
            val month = backStackEntry.arguments!!.getInt("month")
            MonthlyReportScreen(
                year = year,
                month = month,
                onNavigationIconClick = { navController.popBackStack() }
            )
        }
        composable(
            route = Incomes.route,
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutHorizontally() }
        ) {
            IncomesScreen()
        }
        composable(
            route = Investments.route,
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutHorizontally() }
        ) {
            InvestmentsScreen()
        }
        composable(
            route = Expenditures.route,
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutHorizontally() }
        ) {
            ExpendituresScreen()
        }
    }
}