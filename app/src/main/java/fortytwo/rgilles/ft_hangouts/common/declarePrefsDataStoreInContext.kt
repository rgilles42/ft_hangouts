package fortytwo.rgilles.ft_hangouts.common

import android.content.Context
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(
    name = "ftHangoutsPrefs"
)
object PreferencesKeys {
    val COLOUR_VALUE = longPreferencesKey("colourValue")
}

val colourMap = mapOf(
    "Material Default" to 0,
    "Vivid Purple" to 0xFFAA11AA,
    "Deep Blue"     to 0xFF1188FF,
    "Vibrant Orange"  to 0xFFFF7722,
)