package com.anput.personalfinance.ui.screens.assetforecasts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anput.personalfinance.data.repositories.BalanceReportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AssetForecastsViewModel
@Inject constructor(
    private val _balanceReportsRepository: BalanceReportsRepository
): ViewModel() {
    private val _uiStateFlow = MutableStateFlow(AssetForecastsUiState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            initSelectableYears()
        }
    }

    private fun initSelectableYears() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        _uiStateFlow.update {
            it.copy(years = (currentYear..currentYear + 9).toList())
        }
    }


    fun getAllMonthlyReports(year: Int) {
        viewModelScope.launch {
            _balanceReportsRepository
                .getAllMonthlyReports(year)
                .catch { e -> Log.d("AssetForecastsViewModel", "${e.message}") }
                .collect { monthlyReports ->
                    _uiStateFlow.update {
                        it.copy(monthlyReports = monthlyReports)
                    }
                }
        }
    }

}