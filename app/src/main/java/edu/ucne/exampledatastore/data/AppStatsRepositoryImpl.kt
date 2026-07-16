package edu.ucne.exampledatastore.data

import androidx.datastore.core.DataStore
import edu.ucne.exampledatastore.AppStatsProto
import edu.ucne.exampledatastore.domain.repository.AppStatsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class AppStatsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<AppStatsProto>
) : AppStatsRepository {

    override val appStatsFlow: Flow<AppStatsProto> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(AppStatsProto.getDefaultInstance())
            } else {
                throw exception
            }
        }

    override suspend fun recordAppOpen(timestamp: Long) {
        dataStore.updateData { currentStats ->
            currentStats.toBuilder()
                .setOpenCount(currentStats.openCount + 1)
                .setLastOpenedTimestamp(timestamp)
                .build()
        }
    }
}