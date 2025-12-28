package pratheekv39.bridgelearn.io.domain.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// --- GANTI IMPORT INI ---
import com.google.firebase.auth.FirebaseAuth // <-- Pakai yang standar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// ... (Data class UserProfile, UserPreferences, Achievement TIDAK BERUBAH) ...
// ... (Salin saja data class dari kode sebelumnya jika perlu, atau biarkan yang ada) ...

data class UserProfile(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val grade: String = "",
    val bio: String = "",
    val completedLessons: Int = 0,
    val quizzesTaken: Int = 0,
    val averageScore: Float = 0f,
    val learningStreak: Int = 0,
    val preferences: UserPreferences = UserPreferences()
)

data class UserPreferences(
    val notificationsEnabled: Boolean = true,
    val language: String = "English",
    val studyReminderTime: String = "18:00",
    val accessibilityOptions: AccessibilityOptions = AccessibilityOptions()
)

data class AccessibilityOptions(
    val fontSize: Float = 1.0f,
    val highContrast: Boolean = false,
    val screenReader: Boolean = false
)

data class Achievement(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val icon: @Composable () -> Unit = {},
    val isUnlocked: Boolean = false,
    val progress: Float = 0f
)

class ProfileViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// =====================================================================
// PROFILE VIEWMODEL (VERSI "ANTI-ERROR")
// =====================================================================
class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    // GUNAKAN INSTANCE STANDAR (Bukan KTX)
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private val sharedPreferences = application.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

    private val _userProfile = MutableStateFlow(UserProfile(name = "Loading..."))
    val userProfile = _userProfile.asStateFlow()

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri = _profileImageUri.asStateFlow()

    // Data Achievement Dummy
    val achievements = listOf(
        Achievement("1", "Quick Learner", "Complete 5 lessons", { Icon(Icons.Default.Star, null, tint = Color.White) }, true, 1f),
        Achievement("2", "Quiz Master", "Score 100%", { Icon(Icons.Default.EmojiEvents, null, tint = Color.White) }, false, 0.6f)
    )

    init {
        val savedUriString = sharedPreferences.getString("profileImageUri", null)
        _profileImageUri.value = savedUriString?.let { Uri.parse(it) }

        // Panggil fungsi pemantau login yang baru
        observeAuthStateAndProfile()
    }

    // --- FUNGSI BARU: MENGGUNAKAN LISTENER STANDAR ---
    // Ini tidak butuh 'authStateFlow' jadi tidak akan error merah lagi.
    private fun observeAuthStateAndProfile() {
        val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser

            if (user != null) {
                // --- User Login ---
                val userId = user.uid
                Log.d("ProfileViewModel", "User Logged In: $userId")

                // Ambil data profil (gunakan coroutine scope karena ini async)
                viewModelScope.launch {
                    try {
                        val documentSnapshot = db.collection("users").document(userId).get().await()
                        if (documentSnapshot.exists()) {
                            val profile = documentSnapshot.toObject<UserProfile>()
                            if (profile != null) {
                                _userProfile.value = profile
                            }
                        } else {
                            _userProfile.value = UserProfile(name = "Profil tidak ditemukan", email = user.email ?: "")
                        }
                    } catch (e: Exception) {
                        Log.e("ProfileViewModel", "Error fetch profile", e)
                        _userProfile.value = UserProfile(name = "Error mengambil data")
                    }
                }
            } else {
                // --- User Logout ---
                Log.d("ProfileViewModel", "User Logged Out")
                _userProfile.value = UserProfile(name = "Error: Not Logged In")
            }
        }

        // Pasang pendengar (listener) ke Firebase
        auth.addAuthStateListener(authListener)
    }

    // Fungsi lain tidak berubah
    fun updateProfileImage(uri: Uri?) {
        _profileImageUri.value = uri
        viewModelScope.launch {
            sharedPreferences.edit().putString("profileImageUri", uri?.toString()).apply()
        }
    }
}