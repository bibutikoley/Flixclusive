package com.flixclusive.feature.mobile.settings.settings_group

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.flixclusive.core.util.exception.safeCall
import com.flixclusive.feature.mobile.settings.KEY_PLAYER_BUFFER_LENGTH_DIALOG
import com.flixclusive.feature.mobile.settings.KEY_PLAYER_BUFFER_SIZE_DIALOG
import com.flixclusive.feature.mobile.settings.KEY_PLAYER_DISK_CACHE_DIALOG
import com.flixclusive.feature.mobile.settings.SettingsItem
import kotlinx.coroutines.launch
import com.flixclusive.core.ui.common.R as UiCommonR
import com.flixclusive.core.util.R as UtilR

@Composable
internal fun currentCacheSettings(
    sizeSummary: String?,
    updateAppCacheSize: () -> Unit,
): List<SettingsItem> {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    return listOf(
        SettingsItem(
            title = stringResource(UtilR.string.video_cache_size),
            description = stringResource(UtilR.string.video_cache_size_label),
            dialogKey = KEY_PLAYER_DISK_CACHE_DIALOG,
        ),
        SettingsItem(
            title = stringResource(UtilR.string.video_buffer_size),
            description = stringResource(UtilR.string.video_buffer_size_label),
            dialogKey = KEY_PLAYER_BUFFER_SIZE_DIALOG,
        ),
        SettingsItem(
            title = stringResource(UtilR.string.video_buffer_max_length),
            description = stringResource(UtilR.string.video_buffer_max_length_desc),
            dialogKey = KEY_PLAYER_BUFFER_LENGTH_DIALOG,
        ),
        SettingsItem(
            title = stringResource(UtilR.string.clear_app_cache),
            description = sizeSummary,
            onClick = {
                safeCall {
                    scope.launch { context.cacheDir.deleteRecursively() }
                    updateAppCacheSize()
                }
            },
            previewContent = {
                Icon(
                    painter = painterResource(id = UiCommonR.drawable.broom_clean),
                    contentDescription = stringResource(
                        id = UtilR.string.clear_cache_content_desc
                    )
                )
            }
        ),
    )
}