package edu.ucne.exampledatastore.presentation.appstats

data class AppStatsUiState(
    val openCount: Int = 0,
    val formattedLastOpenedDate: String = "",
    val isLoading: Boolean = true
)