package pratheekv39.bridgelearn.io

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pratheekv39.bridgelearn.io.data.UserPreferences // Pastikan import ini benar
import pratheekv39.bridgelearn.io.navigation.NavGraph
import pratheekv39.bridgelearn.io.theme.HomeTheme
import pratheekv39.bridgelearn.io.theme.ThemeViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- 1. INISIALISASI FIREBASE MANUAL (Jalan Terakhir) ---
        try {
            // Cek apakah sudah ada instance agar tidak crash jika init double
            if (FirebaseApp.getApps(this).isEmpty()) {
                val options = FirebaseOptions.Builder()
                    .setApiKey("AIzaSyDfhJmx-Y4r6cG9RD_0agCWHitj4Bz5Vkug") // API Key
                    .setApplicationId("1:463306766705:android:2cdabaf2f7b84f48121ed0") // App ID
                    .setProjectId("bridgelearn-08") // Project ID
                    .build()

                FirebaseApp.initializeApp(this, options)
                Log.d("FirebaseManual", "✅ Firebase berhasil di-inisialisasi manual!")
            } else {
                Log.d("FirebaseManual", "ℹ️ Firebase sudah ter-inisialisasi sebelumnya.")
            }
        } catch (e: Exception) {
            Log.e("FirebaseManual", "❌ Gagal inisialisasi manual: ${e.message}")
        }

        // --- 2. INISIALISASI UserPreferences (INI YANG TADI HILANG) ---
        // Kita butuh context 'this' untuk membuat instance UserPreferences
        val userPreferences = UserPreferences(this)

        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val darkMode by themeViewModel.darkMode.collectAsState()

            HomeTheme(darkTheme = darkMode) {
                Surface {
                    NavGraph(
                        userPreferences = userPreferences, // ✅ Sekarang variabel ini sudah ada
                        themeViewModel = themeViewModel
                    )
                }
            }
        }
    }

    // Fungsi test optional (tidak dipanggil di atas, tapi boleh disimpan)
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
                Log.d("FirebaseTest", "✅ KONEKSI BERHASIL: Data berhasil ditulis ke Firestore.")
            }
            .addOnFailureListener { e ->
                Log.w("FirebaseTest", "❌ KONEKSI GAGAL: Error menulis ke Firestore.", e)
            }
    }
}