package pratheekv39.bridgelearn.io.data

import java.util.Date

data class QuizResult(
    // Tambahkan nilai default (= "") atau (= 0) untuk SEMUA variabel
    // agar Firestore bisa membacanya tanpa error.

    val id: String = "",
    val subjectId: String = "",
    val quizName: String = "",
    val score: Int = 0,
    val totalQuestions: Int = 0,
    val userId: String = "", // Tambahkan ini juga agar aman
    val timestamp: Date? = null // Gunakan nullable (?) dan null sebagai default
)