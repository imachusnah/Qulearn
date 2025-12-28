package pratheekv39.bridgelearn.io.ui.screens.quiz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pratheekv39.bridgelearn.io.R
import pratheekv39.bridgelearn.io.data.QuizResult
import pratheekv39.bridgelearn.io.ui.screens.home.Subject
import pratheekv39.bridgelearn.io.ui.screens.profile.HistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizListScreen(
    navController: NavController,
    subjectId: String,
    onNavigateBack: () -> Unit,
    onQuizClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = viewModel()
) {
    // Ambil data history
    val fullHistoryList by viewModel.historyList.collectAsState()

    // Tentukan data subject
    val subject = remember(subjectId) {
        when (subjectId) {
            "physics" -> Subject("physics", "Physics", "Learn about forces and energy", R.drawable.magnet_straight)
            "chemistry" -> Subject("chemistry", "Chemistry", "Explore matter and reactions", R.drawable.flask)
            "biology" -> Subject("biology", "Biology", "Learn about human body and animals", R.drawable.flower)
            "math" -> Subject("math", "Math", "Learn about different math tools", R.drawable.math_operations)
            else -> Subject("unknown", "Unknown Subject", "Subject not found", R.drawable.ic_launcher_foreground)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${subject.name} Quizzes") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding -> // <-- Parameter ini sangat PENTING agar tidak tertumpuk

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // 1. Terapkan padding dari Scaffold di sini
                .padding(horizontal = 16.dp), // 2. Tambahkan padding kiri-kanan
            verticalArrangement = Arrangement.spacedBy(12.dp), // Jarak antar item lebih renggang
            contentPadding = PaddingValues(vertical = 16.dp) // Padding atas-bawah tambahan
        ) {

            // --- ITEM 1: Overview Card ---
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF694D72)),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Overview",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = subject.description,
                            color = Color.White.copy(alpha = 0.9f),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            // --- ITEM 2: Header Text ---
            item {
                Text(
                    text = "QUIZ LIST",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // --- ITEM 3: Daftar Kuis (Looping) ---
            items(5) { index ->
                val quizNumber = index + 1
                val quizName = "Quiz $quizNumber"

                // Logika mencari nilai terbaik
                val bestResult = fullHistoryList
                    .filter { it.subjectId == subjectId && it.quizName == quizName }
                    .maxByOrNull { it.score }

                QuizItemCard(
                    title = quizName,
                    description = "Latihan soal untuk materi bagian $quizNumber",
                    result = bestResult,
                    onClick = { onQuizClick(quizNumber) }
                )
            }
        }
    }
}

@Composable
private fun QuizItemCard(
    title: String,
    description: String,
    result: QuizResult?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF684B70)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Kolom Kiri (Judul & Deskripsi)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 1
                )
            }

            // Spacer agar nilai tidak nempel teks
            Spacer(modifier = Modifier.width(16.dp))

            // Kolom Kanan (Nilai)
            if (result != null) {

                val score = (result.score.toFloat() / result.totalQuestions.toFloat()) * 100
                // Jika sudah dikerjakan: Tampilkan Skor
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = score.toInt().toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Selesai (${result.score}/${result.totalQuestions})",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Green
                    )
                }
            } else {
                // Jika belum dikerjakan: Tanda Strip
                Text(
                    text = "- / -",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
        }
    }
}