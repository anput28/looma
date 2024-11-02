package me.looma.ui.screens.monthlyreport

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.looma.data.repositories.BalanceReportsRepository
import javax.inject.Inject

@HiltViewModel
class MonthlyReportViewModel
@Inject constructor(
    private val _balanceReportsRepository: BalanceReportsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiStateFlow = MutableStateFlow(MonthlyReportUiState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    init {
        val month = savedStateHandle.get<Int>("month")!!
        viewModelScope.launch {
            getMonthlyReport(month)
        }
    }

    private fun getMonthlyReport(month: Int) {
        val report = _balanceReportsRepository.monthlyReports.find { it.month == month }

        if(report != null) {
            _uiStateFlow.update {
                it.copy(
                    actualBalance = report.actualBalance,
                    plannedBalance = report.plannedBalance,
                    actualSaving = report.actualSaving,
                    plannedSaving = report.plannedSaving,
                    incomes = report.incomes.total,
                    actualOutcomes = report.actualOutcomes.total,
                    plannedOutcomes = report.plannedOutcomes.total,
                    actualInvestments = report.actualInvestments.total,
                    plannedInvestments = report.plannedInvestments.total,
                    actualOtherBalances = report.actualOtherBalances.total,
                    plannedOtherBalances = report.plannedOtherBalances.total
                )
            }
        }

    }
}