package pratheekv39.bridgelearn.io.ui.simulations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.*

// --- WARNA TEMA ---
// Warna Ungu yang konsisten dengan halaman Home/Login Anda
private val PurpleBackground = Color(0xFF6E5175) // Ungu Utama
private val PurpleCard = Color(0xFF4A3050)       // Ungu Lebih Gelap untuk Kartu
private val PurpleAccent = Color(0xFFD1C4E9)     // Ungu Pucat untuk Teks/Slider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendulumScreen(navController: NavController) {

    // --- STATE FISIKA ---
    var length by remember { mutableFloatStateOf(2f) }
    var initialAngle by remember { mutableFloatStateOf(45f) }

    var isRunning by remember { mutableStateOf(false) }
    var time by remember { mutableFloatStateOf(0f) }
    val gravity = 9.8f

    // --- LOGIKA ANIMASI ---
    LaunchedEffect(isRunning) {
        val startTime = System.nanoTime()
        var lastFrameTime = startTime

        while (isRunning) {
            val currentFrameTime = System.nanoTime()
            val deltaTime = (currentFrameTime - lastFrameTime) / 1_000_000_000f
            lastFrameTime = currentFrameTime
            time += deltaTime
            withFrameNanos { }
        }
    }

    // --- RUMUS ---
    val period = 2 * PI * sqrt(length / gravity)
    val angularVelocity = sqrt(gravity / length)
    val currentAngleRad = if (isRunning) {
        Math.toRadians(initialAngle.toDouble()) * cos(angularVelocity * time)
    } else {
        Math.toRadians(initialAngle.toDouble())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pendulum Simulation", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PurpleBackground)
            )
        },
        containerColor = PurpleBackground // Background Ungu Utama
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- 1. PENJELASAN (KARTU TRANSALUCENT) ---
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Konsep Gerak Bandul",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Yellow // Judul Kuning agar kontras
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Simulasi ini menggambarkan gerak bandul. Periodenya ditentukan oleh panjang tali dan percepatan gravitasi.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Rumus
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(0.1f), RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "T = 2π √(L/g)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Cyan
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Ketika kamu mengubah panjang tali atau sudut awal, gerak ayunan bandul ikut berubah. Tampilan Periode: menunjukkan waktu yang dibutuhkan bandul untuk satu ayunan penuh.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            // --- 2. KONTROL SLIDER ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PurpleCard), // Kartu Ungu Gelap
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Panjang Tali: ${String.format("%.1f", length)} m", color = Color.White, fontWeight = FontWeight.Bold)
                    Slider(
                        value = length,
                        onValueChange = { length = it; if (!isRunning) time = 0f },
                        valueRange = 0.5f..5f,
                        colors = SliderDefaults.colors(thumbColor = Color.Cyan, activeTrackColor = Color.Cyan, inactiveTrackColor = Color.White.copy(0.3f))
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Sudut Awal: ${initialAngle.toInt()}°", color = Color.White, fontWeight = FontWeight.Bold)
                    Slider(
                        value = initialAngle,
                        onValueChange = { initialAngle = it; if (!isRunning) time = 0f },
                        valueRange = 10f..90f,
                        colors = SliderDefaults.colors(thumbColor = Color.Magenta, activeTrackColor = Color.Magenta, inactiveTrackColor = Color.White.copy(0.3f))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- 3. TOMBOL KONTROL (WARNA-WARNI) ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Tombol START (Hijau)
                Button(
                    onClick = { isRunning = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Icon(Icons.Default.PlayArrow, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Start")
                }

                // Tombol STOP (Merah)
                Button(
                    onClick = { isRunning = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                ) {
                    Icon(Icons.Default.Stop, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Stop")
                }

                // Tombol RESET (Kuning/Oranye)
                Button(
                    onClick = { isRunning = false; time = 0f },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                ) {
                    Icon(Icons.Default.Refresh, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Reset")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Info Periode
            Text(
                text = "Period: ${String.format("%.2f", period)} s",
                color = Color.Yellow,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- 4. VISUALISASI ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val centerX = size.width / 2
                    val startY = 0f
                    val scale = 50f
                    val ropePixel = length * scale

                    val bobX = centerX + (ropePixel * sin(currentAngleRad)).toFloat()
                    val bobY = startY + (ropePixel * cos(currentAngleRad)).toFloat()

                    // Tali
                    drawLine(Color.White, Offset(centerX, startY), Offset(bobX, bobY), strokeWidth = 6f)
                    // Pivot
                    drawCircle(Color.Yellow, 12f, Offset(centerX, startY))
                    // Bola
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.Yellow, Color(0xFFFF6F00)),
                            center = Offset(bobX - 8, bobY - 8),
                            radius = 50f
                        ),
                        radius = 35f,
                        center = Offset(bobX, bobY)
                    )
                }
            }
        }
    }
}