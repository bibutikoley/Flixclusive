package com.flixclusive.core.datastore

import androidx.datastore.core.DataStore
import com.flixclusive.core.util.coroutines.AppDispatchers.Companion.launchOnIO
import com.flixclusive.model.datastore.AppSettings
import com.flixclusive.model.datastore.AppSettingsProvider
import com.flixclusive.model.datastore.OnBoardingPreferences
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AppSettingsManager @Inject constructor(
    val appSettings: DataStore<AppSettings>,
    val providerSettings: DataStore<AppSettingsProvider>,
    val onBoardingPreferences: DataStore<OnBoardingPreferences>
) {
    /**
     *
     * Used for initial [AppSettings] values.
     * */
    var cachedAppSettings: AppSettings = AppSettings()
        private set

    /**
     *
     * Used for initial [AppSettingsProvider] values.
     * */
    var cachedProviderSettings: AppSettingsProvider = AppSettingsProvider()
        private set

    /**
     *
     * Used for initial [cachedOnBoardingPreferences] values.
     * */
    var cachedOnBoardingPreferences: OnBoardingPreferences = OnBoardingPreferences()
        private set

    init {
        launchOnIO {
            cachedAppSettings = appSettings.data.first()
            cachedProviderSettings = providerSettings.data.first()
            cachedOnBoardingPreferences = onBoardingPreferences.data.first()
        }
    }

    suspend fun updateSettings(newValue: AppSettings) {
        updateSettings { newValue }
    }

    suspend fun updateSettings(transform: suspend (t: AppSettings) -> AppSettings) {
        appSettings.updateData {
            val newSettings = transform(it)

            cachedAppSettings = newSettings
            newSettings
        }
    }

    suspend fun updateProviderSettings(transform: suspend (t: AppSettingsProvider) -> AppSettingsProvider) {
        providerSettings.updateData {
            val newSettings = transform(it)

            cachedProviderSettings = newSettings
            newSettings
        }
    }

    suspend fun updateOnBoardingPreferences(transform: suspend (t: OnBoardingPreferences) -> OnBoardingPreferences) {
        onBoardingPreferences.updateData {
            val newSettings = transform(it)

            cachedOnBoardingPreferences = newSettings
            newSettings
        }
    }
}