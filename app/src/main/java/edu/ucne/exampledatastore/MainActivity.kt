package edu.ucne.exampledatastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.exampledatastore.presentation.appstats.AppStatsScreen
import edu.ucne.exampledatastore.presentation.appstats.AppStatsUiEvent
import edu.ucne.exampledatastore.presentation.appstats.AppStatsViewModel
import edu.ucne.exampledatastore.ui.theme.ExampleDataStoreTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExampleDataStoreTheme {
                val viewModel: AppStatsViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState()
                AppStatsScreen(
                    state = state,
                    onRecordOpen = { viewModel.processIntent(AppStatsUiEvent.RecordAppOpen)}
                )
            }
        }
    }
}