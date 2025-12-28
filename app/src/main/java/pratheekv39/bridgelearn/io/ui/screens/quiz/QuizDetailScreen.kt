package pratheekv39.bridgelearn.io.ui.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pratheekv39.bridgelearn.io.data.QuizRepository
import pratheekv39.bridgelearn.io.data.QuizResult
import pratheekv39.bridgelearn.io.ui.screens.profile.HistoryViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizDetailScreen(
    navController: NavController,
    subjectId: String,
    quizId: Int,
    onNavigateBack: () -> Unit,
    historyViewModel: HistoryViewModel = viewModel()
) {
    // 2. Ambil soal ASLI dari QuizRepository berdasarkan subjectId & quizId
    val questions = remember(subjectId, quizId) {
        QuizRepository.getQuestionsForQuiz(subjectId, quizId)
    }

    // State untuk logika kuis
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswerChecked by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    var isQuizFinished by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "${subjectId.replaceFirstChar { it.uppercase() }} - Quiz $quizId",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- LOGIKA TAMPILAN ---
            if (questions.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Soal belum tersedia untuk kuis ini.", color = Color.Gray)
                }
            } else if (isQuizFinished) {
                // --- TAMPILAN HASIL (SCORE) ---
                QuizResultView(
                    score = score,
                    totalQuestions = questions.size,
                    onBackToHome = {
                        // Simpan ke History sebelum keluar
                        val result = QuizResult(
                            id = System.currentTimeMillis().toString(),
                            subjectId = subjectId,
                            quizName = "Quiz $quizId",
                            score = score,
                            totalQuestions = questions.size,
                            timestamp = Date()
                        )
                        historyViewModel.addHistory(result)
                        navController.popBackStack() // Kembali ke list
                    }
                )
            } else {
                // --- TAMPILAN SOAL (QUESTION) ---
                val currentQuestion = questions[currentQuestionIndex]

                // Indikator Progress (Contoh: Question 1 of 5)
                Text(
                    text = "Question ${currentQuestionIndex + 1} of ${questions.size}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 16.dp)
                )

                // Kartu Soal (Ungu)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF694D72)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = currentQuestion.questionText,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Pilihan Jawaban
                        currentQuestion.options.forEachIndexed { index, optionText ->
                            val isSelected = selectedOptionIndex == index
                            val isCorrect = index == currentQuestion.correctAnswerIndex

                            // Tentukan warna tombol
                            val buttonColor = when {
                                isAnswerChecked && isCorrect -> Color(0xFF4CAF50) // Hijau (Benar)
                                isAnswerChecked && isSelected && !isCorrect -> Color(0xFFE53935) // Merah (Salah)
                                isSelected -> Color.White // Putih (Dipilih user)
                                else -> Color.White.copy(alpha = 0.9f) // Default
                            }

                            val textColor = if (isAnswerChecked && (isCorrect || isSelected)) Color.White else Color.Black

                            Button(
                                onClick = { if (!isAnswerChecked) selectedOptionIndex = index },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp), // Hapus .height(50.dp) di sini
                                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                                shape = RoundedCornerShape(25.dp)
                            ) {
                                // Tambahkan padding internal pada teks agar lebih aman
                                Text(
                                    text = optionText,
                                    color = textColor,
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp) // Padding Vertikal untuk teks
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Tombol "Check" atau "Next"
                Button(
                    onClick = {
                        if (!isAnswerChecked) {
                            // 1. Cek Jawaban
                            if (selectedOptionIndex != null) {
                                isAnswerChecked = true
                                if (selectedOptionIndex == currentQuestion.correctAnswerIndex) {
                                    score++
                                }
                            }
                        } else {
                            // 2. Lanjut ke Soal Berikutnya
                            if (currentQuestionIndex < questions.size - 1) {
                                currentQuestionIndex++
                                selectedOptionIndex = null
                                isAnswerChecked = false
                            } else {
                                isQuizFinished = true
                            }
                        }
                    },
                    enabled = selectedOptionIndex != null, // Disable jika belum pilih
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = if (isAnswerChecked) {
                            if (currentQuestionIndex < questions.size - 1) "Next Question" else "Finish Quiz"
                        } else "Check Answer"
                    )
                }
            }
        }
    }
}

@Composable
fun QuizResultView(score: Int, totalQuestions: Int, onBackToHome: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color(0xFF4CAF50),
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Quiz Completed!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Your Score: $score / $totalQuestions",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onBackToHome, modifier = Modifier.fillMaxWidth(0.7f)) {
            Text("Back to List")
        }
    }
}