package edu.ucne.exampledatastore.domain.repository

import edu.ucne.exampledatastore.AppStatsProto
import kotlinx.coroutines.flow.Flow

interface AppStatsRepository {
    val appStatsFlow: Flow<AppStatsProto>
    suspend fun recordAppOpen(timestamp: Long)
}