package me.looma.ui.screens.assetforecasts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import me.looma.data.repositories.BalanceReportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.looma.ui.DataState
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
        _uiStateFlow.update { it.copy(monthlyReportsState = DataState.Loading) }

        viewModelScope.launch {
            _balanceReportsRepository
                .getAllMonthlyReports(year)
                .catch {
                    _uiStateFlow.update { it.copy( monthlyReportsState = DataState.Error) }
                }
                .collect { monthlyReports ->
                    _uiStateFlow.update {
                        it.copy(monthlyReportsState = DataState.Loaded(monthlyReports))
                    }
                }
        }
    }

}