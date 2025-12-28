package pratheekv39.bridgelearn.io.ui.simulations

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.abs

// ======================================================================
// 🎨 WARNA TEMA (FULL UNGU - KONSISTEN)
// ======================================================================
private val AppBackground = Color(0xFF6E5175)        // Ungu Brand (Background Utama)
private val CardBackground = Color(0xFF4A3050)       // Ungu Lebih Gelap (Kartu)
private val TextColorWhite = Color.White
private val HydrogenColor = Color(0xFF00BFFF)   // Warna H (Biru Cerah)
private val ChlorineColor = Color(0xFFE94560)   // Warna Cl (Merah Muda)
private val HighlightColor = Color(0xFFF8B400) // Kuning/Emas untuk Teks Penting
private val AccentColor = Color(0xFFD1C4E9)    // Ungu Pucat (Aksen Slider)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChemicalBonding(navController: NavController) {

    // --- STATE SIMULASI ---
    var hydrogenPosition by remember { mutableFloatStateOf(0.1f) } // Posisi H (0.0 - 1.0)
    var chlorinePosition by remember { mutableFloatStateOf(0.9f) } // Posisi Cl (0.0 - 1.0)

    val bondThreshold = 0.2f // Jarak minimum agar ikatan terbentuk
    val isBonded = abs(hydrogenPosition - chlorinePosition) < bondThreshold

    Scaffold(
        containerColor = AppBackground, // Background Ungu
        topBar = {
            TopAppBar(
                title = { Text("Simulasi Ikatan Kimia", color = TextColorWhite, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Kembali", tint = TextColorWhite)
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

            // --- 1. KARTU PENJELASAN ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Chemical Bond Simulation (Simulasi Ikatan Kimia)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = HighlightColor // Kuning
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Simulasi ini menunjukkan bagaimana dua atom—Hidrogen (H) dan Klorin (Cl)—dapat digerakkan menggunakan slider untuk mendekat satu sama lain hingga terbentuk **ikatan kovalen** (berbagi elektron).",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextColorWhite,

                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Ketika atom H dan Cl saling mendekat (jarak kurang dari 0.2 unit), mereka akan membentuk molekul HCl (Asam Klorida).",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextColorWhite.copy(alpha = 0.8f),

                    )
                }
            }

            // --- 2. VISUALISASI ATOM ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(CardBackground, RoundedCornerShape(16.dp)), // Background area simulasi
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasWidth = size.width
                    val yCenter = size.height / 2

                    val hPos = hydrogenPosition * canvasWidth
                    val clPos = chlorinePosition * canvasWidth

                    // Gambar Ikatan jika terbentuk
                    if (isBonded) {
                        drawLine(
                            color = Color.White.copy(alpha = 0.7f),
                            start = Offset(hPos, yCenter),
                            end = Offset(clPos, yCenter),
                            strokeWidth = 10f // Garis ikatan lebih tebal
                        )
                    }

                    // --- Gambar Atom Hidrogen (H) ---
                    drawCircle(color = HydrogenColor, radius = 40f, center = Offset(hPos, yCenter))
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            "H", hPos, yCenter + 15.sp.toPx() / 2,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.WHITE
                                textSize = 20.sp.toPx()
                                textAlign = android.graphics.Paint.Align.CENTER
                                isFakeBoldText = true
                            }
                        )
                    }

                    // --- Gambar Atom Klorin (Cl) ---
                    drawCircle(color = ChlorineColor, radius = 40f, center = Offset(clPos, yCenter))
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            "Cl", clPos, yCenter + 15.sp.toPx() / 2,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.WHITE
                                textSize = 20.sp.toPx()
                                textAlign = android.graphics.Paint.Align.CENTER
                                isFakeBoldText = true
                            }
                        )
                    }
                }
            }

            // Pesan Status Ikatan
            if (isBonded) {
                Text(
                    "Ikatan Kovalen HCl Terbentuk!",
                    color = HighlightColor,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else {
                Spacer(Modifier.height(56.dp)) // Beri ruang agar tata letak stabil
            }

            // --- 3. KONTROL SLIDER ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Slider Hidrogen
                    Text("Pindahkan Hidrogen (H)", color = TextColorWhite)
                    Slider(
                        value = hydrogenPosition,
                        onValueChange = { hydrogenPosition = it },
                        valueRange = 0f..1f,
                        colors = SliderDefaults.colors(thumbColor = HydrogenColor, activeTrackColor = HydrogenColor)
                    )

                    Spacer(Modifier.height(16.dp))

                    // Slider Klorin
                    Text("Pindahkan Klorin (Cl)", color = TextColorWhite)
                    Slider(
                        value = chlorinePosition,
                        onValueChange = { chlorinePosition = it },
                        valueRange = 0f..1f,
                        colors = SliderDefaults.colors(thumbColor = ChlorineColor, activeTrackColor = ChlorineColor)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Tombol Reset (Warna Konsisten dengan Tombol Reset Lain)
            Button(
                onClick = {
                    hydrogenPosition = 0.1f
                    chlorinePosition = 0.9f
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)) // Oranye/Reset
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Reset")
                Spacer(Modifier.width(8.dp))
                Text("Reset")
            }
        }
    }
}