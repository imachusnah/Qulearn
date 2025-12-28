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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.*

// ======================================================================
// 🎨 WARNA TEMA (FULL UNGU - KONSISTEN)
// ======================================================================
private val AppBackground = Color(0xFF6E5175)        // Ungu Brand
private val CardBackground = Color(0xFF4A3050)       // Ungu Lebih Gelap
private val TextColorWhite = Color.White
private val AccentColor = Color(0xFFD1C4E9)          // Ungu Pucat (Slider)
private val SpringColor = Color(0xFF00E5FF)          // Cyan (Warna Pegas agar kontras)
private val HighlightColor = Color(0xFFFFFF00)       // Kuning (Judul)

// ======================================================================
// 📝 TEKS PENJELASAN
// ======================================================================
private const val SpringTitle = "Spring Simulation - Hukum Hooke"
private const val SpringExplanation =
    "Ini adalah simulasi gerak harmonik pegas berdasarkan Hukum Hooke: F = −kx.\n\n" +
            "Kecepatan osilasi pegas bergantung pada konstanta pegas (k) dan massa (m). Periodenya:\n" +
            "T = 2π √(m/k)\n\n" +
            "Jika konstanta pegas besar atau massa kecil, pegas berosilasi lebih cepat. Simulasi menampilkan benda yang bergerak naik-turun dengan periode tertentu sesuai pengaturan."

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpringScreen(navController: NavController) {

    // --- STATE SIMULASI ---
    var springConstant by remember { mutableFloatStateOf(50f) } // k (N/m)
    var mass by remember { mutableFloatStateOf(2f) }            // m (kg)
    var damping by remember { mutableFloatStateOf(0.1f) }       // b (Redaman)
    var initialDisplacement by remember { mutableFloatStateOf(100f) } // Amplitudo (pixel)

    var isRunning by remember { mutableStateOf(false) }
    var time by remember { mutableFloatStateOf(0f) }

    // --- RUMUS FISIKA ---
    // Omega (Kecepatan Sudut) = akar(k / m)
    val angularFrequency = sqrt(springConstant / mass)
    // Periode (T) = 2 * pi * akar(m / k)
    val period = 2 * PI * sqrt(mass / springConstant)

    // Posisi y(t) = A * exp(-damping * t) * cos(omega * t)
    // Menggunakan cosinus agar dimulai dari simpangan terjauh (bawah)
    val currentDisplacement = if (isRunning) {
        (initialDisplacement * exp(-damping * time) * cos(angularFrequency * time)).toFloat()
    } else {
        initialDisplacement // Posisi awal (ditarik ke bawah)
    }

    // --- LOGIKA LOOP ANIMASI ---
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

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = { Text("Spring Simulation", color = TextColorWhite, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = TextColorWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppBackground)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 1. BAGIAN PENJELASAN
            ExplanationTextSpring(title = SpringTitle, desc = SpringExplanation)

            // 2. KARTU KONTROL
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Slider Konstanta Pegas (k)
                    Text("Spring Constant (k): ${springConstant.toInt()} N/m", color = TextColorWhite)
                    Slider(
                        value = springConstant,
                        onValueChange = { springConstant = it; if (!isRunning) time = 0f },
                        valueRange = 10f..100f,
                        colors = SliderDefaults.colors(thumbColor = AccentColor, activeTrackColor = AccentColor)
                    )

                    // Slider Massa (m)
                    Text("Mass (m): ${String.format("%.1f", mass)} kg", color = TextColorWhite)
                    Slider(
                        value = mass,
                        onValueChange = { mass = it; if (!isRunning) time = 0f },
                        valueRange = 1f..10f,
                        colors = SliderDefaults.colors(thumbColor = AccentColor, activeTrackColor = AccentColor)
                    )

                    // Slider Redaman (Damping)
                    Text("Damping: ${String.format("%.2f", damping)}", color = TextColorWhite)
                    Slider(
                        value = damping,
                        onValueChange = { damping = it; if (!isRunning) time = 0f },
                        valueRange = 0f..1f,
                        colors = SliderDefaults.colors(thumbColor = AccentColor, activeTrackColor = AccentColor)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. TOMBOL KONTROL (Warna-Warni seperti Pendulum)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { isRunning = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Hijau
                ) {
                    Icon(Icons.Default.PlayArrow, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Start")
                }
                Button(
                    onClick = { isRunning = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)) // Merah
                ) {
                    Icon(Icons.Default.Stop, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Stop")
                }
                Button(
                    onClick = { isRunning = false; time = 0f },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)) // Oranye
                ) {
                    Icon(Icons.Default.Refresh, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Reset")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Info Periode
            Text(
                text = "Period (T): ${String.format("%.2f", period)} s",
                color = HighlightColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 4. VISUALISASI PEGAS (VERTIKAL)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .background(CardBackground, RoundedCornerShape(16.dp)) // Background Gelap
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val anchorX = canvasWidth / 2
                    val anchorY = 20f // Titik gantung (Atap)

                    // Posisi setimbang (equilibrium) kira-kira di tengah area
                    val equilibriumY = canvasHeight / 2 - 50f

                    // Posisi balok saat ini = Titik Setimbang + Simpangan
                    // Jika simpangan positif, balok turun (memanjang)
                    val blockY = equilibriumY + currentDisplacement

                    // Gambar Atap/Penyangga
                    drawLine(Color.Gray, Offset(anchorX - 60, anchorY), Offset(anchorX + 60, anchorY), strokeWidth = 10f)

                    // Gambar Pegas (Zig-Zag)
                    // Pegas digambar dari anchorY sampai ke bagian atas balok (blockY)
                    drawSpring(anchorY, blockY, 12, 50f)

                    // Gambar Balok (Beban)
                    val blockSize = 60f + (mass * 5) // Ukuran balok sedikit berubah sesuai massa
                    drawRect(
                        color = Color.Cyan, // Warna Balok Kontras
                        topLeft = Offset(anchorX - blockSize / 2, blockY),
                        size = Size(blockSize, blockSize)
                    )
                }
            }
        }
    }
}

// Fungsi helper untuk menggambar pegas zig-zag
private fun DrawScope.drawSpring(startY: Float, endY: Float, segments: Int, width: Float) {
    if (startY >= endY) return // Hindari menggambar jika posisi terbalik/invalid

    val segmentHeight = (endY - startY) / segments
    val path = Path()
    val centerX = center.x

    path.moveTo(centerX, startY)

    for (i in 1..segments) {
        val x = if (i % 2 == 0) centerX - width/2 else centerX + width/2
        val y = startY + i * segmentHeight
        path.lineTo(x, y)
    }
    // Tarik garis terakhir ke tengah (posisi balok)
    path.lineTo(centerX, endY)

    drawPath(
        path = path,
        color = SpringColor, // Menggunakan warna Cyan/Terang agar terlihat di background gelap
        style = Stroke(width = 6f)
    )
}

// Komponen Teks Penjelasan (Helper)
@Composable
private fun ExplanationTextSpring(title: String, desc: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = HighlightColor, // Kuning
            modifier = Modifier.padding(bottom = 4.dp)
        )
        // Kartu transparan untuk teks agar lebih mudah dibaca
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.2f)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                color = TextColorWhite,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}