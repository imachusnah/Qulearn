package pratheekv39.bridgelearn.io.ui.simulations

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random

// ======================================================================
// 🎨 WARNA TEMA (FULL UNGU - KONSISTEN)
// ======================================================================
private val AppBackground = Color(0xFF6E5175)        // Ungu Brand (Background Utama)
private val CardBackground = Color(0xFF4A3050)       // Ungu Lebih Gelap (Kartu Simulasi & Item List)
private val TextColorDark = Color(0xFF311B92)        // Tidak terpakai lagi untuk judul di background putih
private val TextColorWhite = Color.White             // Teks Putih (Default di atas ungu)
private val AccentColor = Color(0xFFD1C4E9)          // Ungu Pucat (Aksen Slider)
private val HighlightColor = Color(0xFFFFFF00)       // Kuning (Highlight)

// Data class untuk contoh benda
data class PhExample(
    val name: String,
    val ph: Float,
    val description: String,
    val icon: String // Emoji
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcidBaseInteractiveLab(navController: NavController) {

    // --- STATE SIMULASI ---
    var phValue by remember { mutableFloatStateOf(7.0f) }

    // Menentukan sifat berdasarkan pH untuk ditampilkan
    val phNature = when {
        phValue < 6.9 -> "Asam (Acidic)"
        phValue > 7.1 -> "Basa (Basic)"
        else -> "Netral (Neutral)"
    }

    // Animasi warna cairan berdasarkan nilai pH
    val liquidColor by animateColorAsState(
        targetValue = when {
            phValue < 3 -> Color(0xFFFF5252) // Merah
            phValue < 6 -> Color(0xFFFFD740) // Kuning
            phValue in 6.0..8.0 -> Color(0xFFE0F7FA) // Bening/Putih
            phValue < 11 -> Color(0xFF40C4FF) // Biru
            else -> Color(0xFF7C4DFF)        // Ungu
        },
        animationSpec = tween(500), label = "ColorAnimation"
    )

    // Daftar Contoh Benda Sehari-hari
    val examples = remember {
        listOf(
            PhExample("Lemon", 2.0f, "Sangat asam. Banyak ion H⁺. Terasa kecut.", "🍋"),
            PhExample("Milk (Susu)", 6.5f, "Sedikit asam (laktosa fermentasi), hampir netral.", "🥛"),
            PhExample("Water (Air)", 7.0f, "Netral. Ion H⁺ dan OH⁻ seimbang.", "💧"),
            PhExample("Salt (Garam)", 7.0f, "Netral saat larut dalam air.", "🧂"),
            PhExample("Cooking Oil", 7.0f, "Netral, tidak asam atau basa.", "🍳"),
            PhExample("Toothpaste", 9.5f, "Basa ringan. Menetralkan asam mulut.", "🦷"),
            PhExample("Soap (Sabun)", 9.8f, "Basa kuat. Efektif mengangkat minyak.", "🧼")
        )
    }

    Scaffold(
        containerColor = AppBackground, // Background UTAMA: Ungu
        topBar = {
            TopAppBar(
                title = { Text("Acids & Bases", color = TextColorWhite, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = TextColorWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardBackground) // TopBar juga Ungu
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- 1. PENJELASAN KONSEP ---
            item {
                // Judul Section (Kuning agar menonjol di latar ungu)
                Text(
                    text = "Acids and Bases (Asam dan Basa)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = HighlightColor, // Kuning
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Kartu Penjelasan (Ungu Gelap Transparan)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardBackground.copy(alpha = 0.5f)),
                    elevation = CardDefaults.cardElevation(2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Konsep tingkat keasaman (pH) pada berbagai benda sehari-hari. Skala pH berada pada rentang 0–14, di mana:\n\n" +
                                    "• pH < 7 → Asam (banyak ion H⁺), ditandai warna merah.\n" +
                                    "• pH = 7 → Netral, seperti air.\n" +
                                    "• pH > 7 → Basa (banyak ion OH⁻), ditandai warna biru.\n\n" +
                                    "Slider “Select pH Level” di bawah memungkinkan Anda memilih nilai pH dan melihat perubahan sifat zat.",
                            color = TextColorWhite,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }

            // --- 2. SIMULASI INTERAKTIF ---
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardBackground), // Ungu Kartu Utama
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Simulasi pH Interaktif", color = TextColorWhite, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                        Spacer(Modifier.height(16.dp))

                        // Visualisasi Gelas Kimia (Beaker)
                        Box(
                            modifier = Modifier
                                .size(120.dp, 160.dp)
                                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                                .background(Color.Black.copy(0.3f)) // Efek Kaca
                                // Modifier.border yang sudah benar
                                .border(
                                    width = 2.dp,
                                    color = Color.White.copy(0.5f),
                                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                                )
                        ) {
                            // Cairan
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.8f)
                                    .align(Alignment.BottomCenter)
                                    .background(liquidColor.copy(alpha = 0.9f))
                            ) {
                                AcidBaseIonParticles(ph = phValue)
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        // Info pH
                        Text("pH Level: ${String.format("%.1f", phValue)}", color = HighlightColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text(phNature, color = TextColorWhite, fontSize = 18.sp, fontWeight = FontWeight.Medium)

                        Spacer(Modifier.height(16.dp))

                        // Slider Pengontrol pH
                        Slider(
                            value = phValue,
                            onValueChange = { phValue = it },
                            valueRange = 0f..14f,
                            colors = SliderDefaults.colors(
                                thumbColor = AccentColor,
                                activeTrackColor = liquidColor, // Track slider ikut warna cairan
                                inactiveTrackColor = Color.White.copy(0.3f)
                            )
                        )

                        // Legenda Ion
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(Modifier.size(12.dp).background(Color.Red.copy(0.8f), CircleShape))
                                Spacer(Modifier.width(4.dp))
                                Text("H⁺ (Asam)", color = TextColorWhite, fontSize = 12.sp)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(Modifier.size(12.dp).background(Color.Blue.copy(0.8f), CircleShape))
                                Spacer(Modifier.width(4.dp))
                                Text("OH⁻ (Basa)", color = TextColorWhite, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // --- 3. DAFTAR CONTOH BENDA ---
            item {
                Text(
                    "Penjelasan Contoh Benda Sehari-hari",
                    color = HighlightColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            items(examples) { example ->
                AcidBaseExampleItem(example) { phValue = example.ph }
            }
        }
    }
}

// --- KOMPONEN HELPER (PRIVATE) ---

@Composable
private fun AcidBaseIonParticles(ph: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        val hPlusCount = if (ph < 7) ((7 - ph) * 5).toInt().coerceAtLeast(2) else 2
        val ohMinCount = if (ph > 7) ((ph - 7) * 5).toInt().coerceAtLeast(2) else 2

        repeat(hPlusCount) {
            drawCircle(color = Color.Red.copy(alpha = 0.8f), radius = 5f, center = Offset(Random.nextFloat() * w, Random.nextFloat() * h))
        }
        repeat(ohMinCount) {
            drawCircle(color = Color.Blue.copy(alpha = 0.8f), radius = 7f, center = Offset(Random.nextFloat() * w, Random.nextFloat() * h))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AcidBaseExampleItem(item: PhExample, onClick: () -> Unit) {
    Card(
        onClick = onClick,modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground), // Kartu Ungu
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            // Ikon Emoji
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White.copy(0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = item.icon, fontSize = 24.sp)
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextColorWhite)
                    Text(text = "pH ${item.ph}", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = HighlightColor)
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextColorWhite.copy(alpha = 0.9f),
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}