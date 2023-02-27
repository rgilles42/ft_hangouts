package fortytwo.rgilles.ft_hangouts.common

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(
    name = "ftHangoutsPrefs"
)

object PreferencesKeys {
    val COLOUR_ID = intPreferencesKey("colourId")
}