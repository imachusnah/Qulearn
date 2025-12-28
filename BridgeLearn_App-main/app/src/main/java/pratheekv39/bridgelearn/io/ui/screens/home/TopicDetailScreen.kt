package pratheekv39.bridgelearn.io.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Upload
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicDetailScreen(navController: NavController, topicId: Int) {
    var textInput by remember { mutableStateOf("") }
    var selectedFileName by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Topic Detail $topicId") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- Deskripsi Utama ---
            Text(
                text = "This is detail for Topic $topicId",
                style = MaterialTheme.typography.bodyLarge
            )

            // --- Tombol Tambah File ---
            Button(
                onClick = {
                    // TODO: Tambahkan file picker di sini (misal dengan intent Android)
                    selectedFileName = "contoh_dokumen.pdf"
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6E5175)
                )
            ) {
                Icon(Icons.Default.Upload, contentDescription = "Tambah File", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Upload File", color = Color.White)
            }

            // --- Nama file yang dipilih (jika ada) ---
            selectedFileName?.let {
                Text(
                    text = "File chosen: $it",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // --- Input teks ---
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Write a note or description...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            // --- Tombol Simpan ---
            Button(
                onClick = {
                    // TODO: Simpan teks & file ke database atau server
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6E5175)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save", color = Color.White)
            }
        }
    }
}
