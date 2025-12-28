package pratheekv39.bridgelearn.io.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pratheekv39.bridgelearn.io.data.QuizResult
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavController,
    // Pastikan HistoryViewModel sudah di-import dengan benar
    viewModel: HistoryViewModel = viewModel()
) {
    val historyList by viewModel.historyList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Kuis") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar kartu
        ) {

            if (historyList.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Anda belum memiliki riwayat kuis.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                // Mengurutkan history dari yang terbaru (opsional)
                val sortedList = historyList.sortedByDescending { it.timestamp }

                items(sortedList) { result ->
                    HistoryItemCard(result = result)
                }
            }
        }
    }
}

@Composable
private fun HistoryItemCard(result: QuizResult) {
    // 1. Hitung Persentase Nilai (0 - 100)
    val scorePercentage = if (result.totalQuestions > 0) {
        (result.score.toFloat() / result.totalQuestions.toFloat()) * 100
    } else {
        0f
    }

    // 2. Format Tanggal agar rapi (contoh: 17 Nov 2025 14:30)
    val formattedDate = remember(result.timestamp) {
        result.timestamp?.let {
            val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
            formatter.format(it)
        } ?: "-"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        // Warna kartu mengikuti warna Primary tema (Biru/Ungu)
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // --- BAGIAN KIRI: INFO KUIS ---
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = result.subjectId.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Kuis: ${result.quizName}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            // --- BAGIAN KANAN: NILAI LINGKARAN ---
            Spacer(modifier = Modifier.width(16.dp)) // Jarak antara teks dan nilai

            Surface(
                shape = CircleShape,
                color = Color.White, // Lingkaran warna putih
                modifier = Modifier.size(60.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Angka Besar (Nilai 0-100)
                        Text(
                            text = scorePercentage.toInt().toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            // Warna teks nilai mengikuti warna kartu (Primary)
                            color = MaterialTheme.colorScheme.primary
                        )
                        // Angka Kecil (Skor Asli 2/2)
                        Text(
                            text = "${result.score}/${result.totalQuestions}",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}