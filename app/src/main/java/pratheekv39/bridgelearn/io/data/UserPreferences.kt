package pratheekv39.bridgelearn.io.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        // HANYA simpan status login dan pengaturan
        val LOGGED_IN_KEY = booleanPreferencesKey("logged_in")
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")
        val LANGUAGE_KEY = stringPreferencesKey("language")
    }

    // Flow data status/settings
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { it[LOGGED_IN_KEY] ?: false }
    val darkMode: Flow<Boolean> = context.dataStore.data.map { it[DARK_MODE_KEY] ?: false }
    val notificationsEnabled: Flow<Boolean> = context.dataStore.data.map { it[NOTIFICATIONS_KEY] ?: true }
    val language: Flow<String> = context.dataStore.data.map { it[LANGUAGE_KEY] ?: "English" }


    // Fungsi untuk menyimpan status login
    suspend fun setLoggedIn(loggedIn: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[LOGGED_IN_KEY] = loggedIn
        }
    }

    // Fungsi untuk menyimpan Settings
    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE_KEY] = enabled
        }
    }

    suspend fun setNotifications(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[NOTIFICATIONS_KEY] = enabled
        }
    }

    suspend fun setLanguage(language: String) {
        context.dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = language
        }
    }

    // Fungsi clear tetap berguna untuk Logout
    suspend fun clearData() {
        context.dataStore.edit { it.clear() }
    }
}