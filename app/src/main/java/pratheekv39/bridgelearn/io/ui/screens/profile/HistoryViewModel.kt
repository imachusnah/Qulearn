package pratheekv39.bridgelearn.io.ui.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pratheekv39.bridgelearn.io.data.QuizResult

class HistoryViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    // State untuk UI
    private val _historyList = MutableStateFlow<List<QuizResult>>(emptyList())
    val historyList = _historyList.asStateFlow()

    init {
        fetchQuizHistory()
    }

    /**
     * FUNGSI 1: MENGAMBIL DATA (READ)
     * Mendengarkan perubahan data secara real-time dari Firestore
     */
    private fun fetchQuizHistory() {
        val currentUserId = auth.currentUser?.uid

        if (currentUserId.isNullOrEmpty()) {
            Log.w("HistoryViewModel", "User belum login, stop fetch.")
            _historyList.value = emptyList()
            return
        }

        // Ambil data quiz_history milik user yang sedang login
        db.collection("quiz_history")
            .whereEqualTo("userId", currentUserId) // Filter hanya punya user ini
            .orderBy("timestamp", Query.Direction.DESCENDING) // Urutkan dari yang terbaru
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("HistoryViewModel", "Gagal mengambil riwayat.", error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val results = snapshot.toObjects(QuizResult::class.java)
                    _historyList.value = results
                    Log.d("HistoryViewModel", "Berhasil memuat ${results.size} riwayat.")
                }
            }
    }

    /**
     * FUNGSI 2: MENYIMPAN DATA (WRITE) - [INI YANG TADI HILANG]
     * Dipanggil dari QuizDetailScreen saat kuis selesai.
     */
    fun addHistory(result: QuizResult) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Kita perlu menambahkan 'userId' agar query fetch di atas bekerja
            val dataToSave = hashMapOf(
                "userId" to currentUser.uid, // KUNCI PENTING: ID User
                "id" to result.id,
                "subjectId" to result.subjectId,
                "quizName" to result.quizName,
                "score" to result.score,
                "totalQuestions" to result.totalQuestions,
                "timestamp" to result.timestamp
            )

            db.collection("quiz_history")
                .add(dataToSave)
                .addOnSuccessListener {
                    Log.d("HistoryViewModel", "Riwayat berhasil disimpan ke Firestore!")
                }
                .addOnFailureListener { e ->
                    Log.e("HistoryViewModel", "Gagal menyimpan riwayat.", e)
                }
        } else {
            Log.w("HistoryViewModel", "User tidak login, tidak bisa simpan riwayat.")
        }
    }
}