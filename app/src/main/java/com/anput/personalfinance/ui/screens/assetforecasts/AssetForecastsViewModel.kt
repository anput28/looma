package com.anput.personalfinance.ui.screens.assetforecasts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AssetForecastsViewModel
@Inject constructor(): ViewModel() {
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
}