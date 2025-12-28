package pratheekv39.bridgelearn.io.domain.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// Data class untuk merangkum state dari setiap simulasi secara bersih
data class FirstLawUiState(val isMoving: Boolean = false)
data class SecondLawUiState(val force: Float = 50f, val mass: Float = 10f)
data class ThirdLawUiState(val isReleased: Boolean = false)

/**
 * ViewModel untuk mengelola semua state dan logika bisnis dari layar Hukum Newton.
 * Ini membuat UI (Composable) menjadi 'bodoh' dan hanya bertugas menampilkan data.
 */
class NewtonsLawsViewModel : ViewModel() {
    // State untuk setiap hukum, hanya bisa diubah dari dalam ViewModel ini.
    var firstLawState by mutableStateOf(FirstLawUiState())
        private set

    var secondLawState by mutableStateOf(SecondLawUiState())
        private set

    var thirdLawState by mutableStateOf(ThirdLawUiState())
        private set

    // --- EVENT HANDLERS (Fungsi yang dipanggil dari UI) ---

    fun onFirstLawToggle() {
        firstLawState = firstLawState.copy(isMoving = !firstLawState.isMoving)
    }

    fun onSecondLawForceChanged(newForce: Float) {
        secondLawState = secondLawState.copy(force = newForce)
    }

    fun onSecondLawMassChanged(newMass: Float) {
        secondLawState = secondLawState.copy(mass = newMass)
    }

    fun onThirdLawToggle() {
        thirdLawState = thirdLawState.copy(isReleased = !thirdLawState.isReleased)
    }

    /**
     * Menjalankan logika simulasi Hukum II Newton.
     * Fungsi ini menerima callback `onAnimate` dari UI untuk memisahkan logika
     * perhitungan (di ViewModel) dari eksekusi animasi (di Composable).
     */
    fun startSecondLawSimulation(onAnimate: suspend (duration: Int) -> Unit) {
        viewModelScope.launch {
            // Rumus Fisika: Durasi berbanding terbalik dengan Percepatan (F/m)
            val acceleration = secondLawState.force / secondLawState.mass
            // Durasi yang lebih pendek = gerakan yang lebih cepat.
            // Dibatasi antara 0.5 detik hingga 5 detik agar animasi tetap terlihat wajar.
            val duration = (5000 / acceleration).roundToInt().coerceIn(500, 5000)

            // Panggil kembali fungsi animasi yang diberikan oleh UI
            onAnimate(duration)
        }
    }
}
