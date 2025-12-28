package pratheekv39.bridgelearn.io.ui.screens.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class QuizDetailViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    // Fungsi ini akan dipanggil setiap kali kuis selesai
    fun saveFirstAttemptResult(
        subjectId: String,
        quizId: Int,
        score: Int,
        totalQuestions: Int,
        onSuccess: () -> Unit // Callback ini wajib dipanggil saat selesai
    ) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val currentUserId = currentUser.uid
            val quizName = "Quiz $quizId"

            // Log untuk debugging
            Log.d("QuizDetailVM", "Mencoba menyimpan skor untuk: $subjectId - $quizName")

            // Data yang akan disimpan
            val resultData = hashMapOf(
                "userId" to currentUserId,
                "id" to System.currentTimeMillis().toString(),
                "subjectId" to subjectId,
                "quizName" to quizName,
                "score" to score,
                "totalQuestions" to totalQuestions,
                "timestamp" to Date()
            )

            // LANGSUNG SIMPAN
            db.collection("quiz_history")
                .add(resultData)
                .addOnSuccessListener {
                    Log.d("QuizDetailVM", "✅ Skor BERHASIL disimpan ke Firestore!")
                    // [PENTING] Panggil callback ini agar layar tahu proses selesai
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    Log.e("QuizDetailVM", "❌ Gagal menyimpan skor", e)
                    // Tetap panggil onSuccess agar user tidak terjebak di layar loading
                    // (Atau bisa tambahkan onError jika ingin menampilkan pesan error)
                    onSuccess()
                }
        } else {
            Log.w("QuizDetailVM", "User belum login!")
            // Jika user tidak login, langsung kembali saja
            onSuccess()
        }
    }
}