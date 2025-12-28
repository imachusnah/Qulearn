package pratheekv39.bridgelearn.io.ui.simulations

// --- IMPORTS ---
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// ======================================================================
// 🎨 WARNA TEMA FULL UNGU
// ======================================================================
private val AppBackground = Color(0xFF6E5175)        // Ungu Brand (Background Utama)
private val CardBackground = Color(0xFF4A3050)       // Ungu Lebih Gelap (Kartu)
private val TextColorWhite = Color.White             // Teks Putih
private val AccentColor = Color(0xFFD1C4E9)          // Ungu Pucat (Slider/Aksen)
private val ButtonColor = Color(0xFF9575CD)          // Ungu Tombol

// ======================================================================
// 1. DEFINISI TEKS PENJELASAN
// ======================================================================
private const val FirstLawTitle = "1. First Law – Hukum I Newton"
private const val FirstLawExplanation =
    "Hukum pertama Newton menyatakan bahwa suatu benda akan tetap diam atau bergerak lurus beraturan kecuali ada gaya luar yang bekerja padanya. Bola putih di bawah diam hingga Anda menekan tombol."

private const val SecondLawTitle = "2. Second Law – Hukum II Newton (F = ma)"
private const val SecondLawExplanation =
    "Percepatan berbanding lurus dengan Gaya (F) dan berbanding terbalik dengan Massa (m). Gunakan slider untuk melihat pengaruh Gaya dan Massa terhadap kecepatan bola."

private const val ThirdLawTitle = "3. Third Law – Hukum III Newton (Aksi = Reaksi)"
private const val ThirdLawExplanation =
    "Setiap aksi memiliki reaksi yang sama besar dan berlawanan arah. Saat udara keluar ke KIRI (Aksi), balon akan terdorong ke KANAN (Reaksi)."


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewtonsLawsScreen(navController: NavController) {

    val scope = rememberCoroutineScope()

    // --- STATE & ANIMASI HUKUM I ---
    var isMoving1 by remember { mutableStateOf(false) }
    val offsetX1 = remember { Animatable(0f) }

    LaunchedEffect(isMoving1) {
        if (isMoving1) {
            offsetX1.animateTo(
                targetValue = 300f,
                animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
            )
        } else {
            offsetX1.snapTo(0f)
        }
    }

    // --- STATE & ANIMASI HUKUM II ---
    var forceValue2 by remember { mutableFloatStateOf(50f) }
    var massValue2 by remember { mutableFloatStateOf(10f) }
    val ballPosition2 = remember { Animatable(0f) }

    fun startSecondLawSimulation() {
        scope.launch {
            ballPosition2.snapTo(0f)
            val accelerationFactor = forceValue2 / massValue2
            val baseDuration = 5000
            val duration = (baseDuration / accelerationFactor).roundToInt().coerceIn(200, 5000)

            ballPosition2.animateTo(
                targetValue = 280f,
                animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing)
            )
        }
    }

    // --- STATE & ANIMASI HUKUM III ---
    var isBalloonReleased by remember { mutableStateOf(false) }
    val balloonOffset3 = remember { Animatable(0f) }
    val airOffset3 = remember { Animatable(0f) }

    LaunchedEffect(isBalloonReleased) {
        if (isBalloonReleased) {
            val duration = 1500
            launch {
                balloonOffset3.animateTo(250f, tween(duration, easing = LinearOutSlowInEasing))
            }
            launch {
                airOffset3.animateTo(-150f, tween(duration, easing = FastOutLinearInEasing))
            }
        } else {
            balloonOffset3.snapTo(0f)
            airOffset3.snapTo(0f)
        }
    }

    // --- UI UTAMA ---
    Scaffold(
        containerColor = AppBackground, // Background Ungu Utama
        topBar = {
            TopAppBar(
                title = { Text("Newton's Laws", color = TextColorWhite, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = TextColorWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppBackground)
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

            // ==========================================
            // KARTU 1: HUKUM I
            // ==========================================
            item {
                // Bagian Penjelasan
                ExplanationText(title = FirstLawTitle, desc = FirstLawExplanation)

                // Kartu Simulasi (Ungu Gelap)
                CardSimulationContainer {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Status: ${if (isMoving1) "Bergerak" else "Diam"}", color = TextColorWhite)
                        Button(
                            onClick = { isMoving1 = !isMoving1 },
                            colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                        ) {
                            Text(if (isMoving1) "Reset" else "Apply Force")
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Area Visualisasi (Hitam Transparan)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Canvas(modifier = Modifier.size(30.dp).offset(x = offsetX1.value.dp).padding(start = 8.dp)) {
                            drawCircle(color = Color.White)
                        }
                    }
                }
            }

            // ==========================================
            //  KARTU 2: HUKUM II
            // ==========================================
            item {
                ExplanationText(title = SecondLawTitle, desc = SecondLawExplanation)

                CardSimulationContainer {
                    // Slider Gaya
                    Text("Force: ${forceValue2.toInt()} N", color = TextColorWhite)
                    Slider(
                        value = forceValue2, onValueChange = { forceValue2 = it },
                        valueRange = 10f..100f,
                        colors = SliderDefaults.colors(thumbColor = AccentColor, activeTrackColor = AccentColor)
                    )

                    // Slider Massa
                    Text("Mass: ${massValue2.toInt()} kg", color = TextColorWhite)
                    Slider(
                        value = massValue2, onValueChange = { massValue2 = it },
                        valueRange = 5f..50f,
                        colors = SliderDefaults.colors(thumbColor = AccentColor, activeTrackColor = AccentColor)
                    )

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = { startSecondLawSimulation() },
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                    ) {
                        Icon(Icons.Default.PlayArrow, "Start", tint = Color.White)
                        Spacer(Modifier.width(4.dp))
                        Text("Start")
                    }

                    Spacer(Modifier.height(16.dp))

                    // Visualisasi Bola Merah
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Canvas(modifier = Modifier
                            .size((10 + massValue2).dp)
                            .offset(x = ballPosition2.value.dp)
                            .padding(start = 8.dp)
                        ) {
                            drawCircle(color = Color.Red)
                        }
                    }
                }
            }

            // ==========================================
            // 💥 KARTU 3: HUKUM III
            // ==========================================
            item {
                ExplanationText(title = ThirdLawTitle, desc = ThirdLawExplanation)

                CardSimulationContainer {
                    Button(
                        onClick = { isBalloonReleased = !isBalloonReleased },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                    ) {
                        Icon(if (isBalloonReleased) Icons.Default.Refresh else Icons.Default.PlayArrow, "Action", tint = Color.White)
                        Spacer(Modifier.width(4.dp))
                        Text(if (isBalloonReleased) "Reset" else "Release Balloon")
                    }

                    Spacer(Modifier.height(32.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth().height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isBalloonReleased) {
                            Canvas(modifier = Modifier.offset(x = airOffset3.value.dp)) {
                                drawCircle(color = Color.White.copy(alpha = 0.5f), radius = 8f, center = Offset(-50f, 0f))
                            }
                        }
                        Canvas(modifier = Modifier.size(60.dp).offset(x = balloonOffset3.value.dp)) {
                            drawOval(color = AccentColor, size = size) // Balon Ungu Muda
                            drawLine(
                                color = Color.White,
                                start = Offset(0f, size.height / 2),
                                end = Offset(-15f, size.height / 2),
                                strokeWidth = 4f
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- KOMPONEN UI PEMBANTU ---

@Composable
fun ExplanationText(title: String, desc: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
        // Judul Kuning agar kontras di background ungu
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Yellow,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        // Penjelasan Putih
        Text(
            text = desc,
            style = MaterialTheme.typography.bodyMedium,
            color = TextColorWhite,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
fun CardSimulationContainer(content: @Composable ColumnScope.() -> Unit) {
    // Kartu Simulasi (Ungu Gelap)
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}