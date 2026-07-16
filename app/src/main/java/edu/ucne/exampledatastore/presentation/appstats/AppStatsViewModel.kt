package edu.ucne.exampledatastore.presentation.appstats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.exampledatastore.domain.repository.AppStatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import jakarta.inject.Inject

@HiltViewModel
class AppStatsViewModel @Inject constructor(
    private val repository: AppStatsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AppStatsUiState())
    val state: StateFlow<AppStatsUiState> = _state.asStateFlow()

    init {
        observeStats()
        recordAppOpen()
    }

    private fun observeStats() {
        viewModelScope.launch {
            repository.appStatsFlow.collect { protoStats ->
                _state.update {
                    it.copy(
                        openCount = protoStats.openCount,
                        formattedLastOpenedDate = formatTimestamp(protoStats.lastOpenedTimestamp),
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun recordAppOpen() {
        viewModelScope.launch {
            repository.recordAppOpen(System.currentTimeMillis())
        }
    }

    fun processIntent(event: AppStatsUiEvent) {
        when (event) {
            is AppStatsUiEvent.RecordAppOpen -> {
                viewModelScope.launch {
                    repository.recordAppOpen(System.currentTimeMillis())
                }
            }
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        if (timestamp == 0L) return "Esta es la primera vez"
        val sdf = SimpleDateFormat("dd/MM/yyyy 'a las' HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}