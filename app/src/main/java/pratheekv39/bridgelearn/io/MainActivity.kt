package pratheekv39.bridgelearn.io

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pratheekv39.bridgelearn.io.data.UserPreferences
import pratheekv39.bridgelearn.io.navigation.NavGraph
import pratheekv39.bridgelearn.io.theme.HomeTheme
import pratheekv39.bridgelearn.io.theme.ThemeViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- 1. INISIALISASI FIREBASE MANUAL ---
        try {
            if (FirebaseApp.getApps(this).isEmpty()) {
                val options = FirebaseOptions.Builder()
                    .setApiKey("AIzaSyDfhJmx-Y4r6cG9RD_0agCWHitj4Bz5Vkug")
                    .setApplicationId("1:463306766705:android:2cdabaf2f7b84f48121ed0")
                    .setProjectId("bridgelearn-08")
                    .build()

                FirebaseApp.initializeApp(this, options)
                Log.d("FirebaseManual", "Firebase berhasil di-inisialisasi manual!")
            } else {
                Log.d("FirebaseManual", "Firebase sudah ter-inisialisasi sebelumnya.")
            }
        } catch (e: Exception) {
            Log.e("FirebaseManual", "Gagal inisialisasi manual: ${e.message}")
        }

        // --- 2. INISIALISASI USERPREFERENCES ---
        val userPreferences = UserPreferences(this)

        setContent {

            val themeViewModel: ThemeViewModel = viewModel()
            val darkMode by themeViewModel.darkMode.collectAsState()

            // -------------------------
            // SYSTEM UI CONTROLLER
            // -------------------------
            val systemUiController = rememberSystemUiController()
            val isDarkTheme = darkMode

            val statusBarColor =
                if (isDarkTheme) Color(0xFF423349) else Color(0xFF423349)   // Ungu pastel
            val navBarColor =
                if (isDarkTheme) Color(0xFF423349) else Color(0xFF423349)

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = statusBarColor,
                    darkIcons = false    // ⬅️ Ikon & teks status bar putih
                )
                systemUiController.setNavigationBarColor(
                    color = navBarColor,
                    darkIcons = false    // ⬅️ Ikon navbar juga putih
                )
            }
            // -------------------------

            HomeTheme(darkTheme = darkMode) {
                Surface {
                    NavGraph(
                        userPreferences = userPreferences,
                        themeViewModel = themeViewModel
                    )
                }
            }
        }
    }

    private fun testFirebaseConnection() {
        val db = Firebase.firestore
        val testData = hashMapOf(
            "status" to "Koneksi Berhasil",
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("test_koneksi")
            .document("ping_1")
            .set(testData)
            .addOnSuccessListener {
                Log.d("FirebaseTest", "KONEKSI BERHASIL: Data berhasil ditulis.")
            }
            .addOnFailureListener { e ->
                Log.w("FirebaseTest", "KONEKSI GAGAL.", e)
            }
    }
}
