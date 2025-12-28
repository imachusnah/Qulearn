package pratheekv39.bridgelearn.io.ui.screens.learn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pratheekv39.bridgelearn.io.R
import pratheekv39.bridgelearn.io.data.models.LearnSubject
import pratheekv39.bridgelearn.io.data.models.LearningContent
import pratheekv39.bridgelearn.io.domain.viewmodels.LearnViewModel

// ==============================================================
// LEARN SCREEN: HANYA DASHBOARD & NAVIGASI (TIDAK ADA SIMULASI)
// ==============================================================

@Composable
fun LearnScreen(
    navController: NavController,
    viewModel: LearnViewModel = viewModel()
) {
    val selectedSubject by viewModel.selectedSubject.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        if (selectedSubject == null) {
            LearnDashboard(
                subjects = viewModel.subjects,
                onSubjectSelect = { viewModel.selectSubject(it) }
            )
        } else {
            SubjectLearningContent(
                subject = selectedSubject!!,
                onBackPress = { viewModel.clearSelectedSubject() },
                navController = navController
            )
        }
    }
}

@Composable
fun LearnDashboard(
    subjects: List<LearnSubject>,
    onSubjectSelect: (LearnSubject) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Interactive Learning",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        items(subjects) { subject ->
            SubjectCard(
                subject = subject,
                onClick = { onSubjectSelect(subject) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectCard(
    subject: LearnSubject,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6E5175) // 💜 Ungu tua
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = subject.drawableResId),
                contentDescription = "${subject.name} Icon",
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = subject.name,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = subject.description,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
                LinearProgressIndicator(
                    progress = subject.learningContent.map { it.progress }.average().toFloat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .height(6.dp),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.4f)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectLearningContent(
    subject: LearnSubject,
    onBackPress: () -> Unit,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(subject.name) },
            navigationIcon = {
                IconButton(onClick = onBackPress) {
                    Icon(Icons.Default.ArrowBack, "Back")
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val groupedContent = subject.learningContent.groupBy { it.type.name }

            groupedContent.forEach { (type, contents) ->
                item {
                    Text(
                        text = type,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                items(contents) { content ->
                    LearningContentCard(content, navController, subject)
                }
            }
        }
    }
}

@Composable
fun LearningContentCard(
    content: LearningContent,
    navController: NavController,
    subject: LearnSubject
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // === NAVIGASI KE SIMULASI TERPISAH ===
                when (content.title) {
                    "Acids and Base Simulator" -> navController.navigate("Interactive")
                    "Pendulum Simulator" -> navController.navigate("Pendulum")
                    "Spring Simulator" -> navController.navigate("Spring")
                    "Periodic Table" -> navController.navigate("PeriodicTable")
                    "Energy Conservation" -> navController.navigate("EnergyConservation")
                    "Newton's Laws" -> navController.navigate("NewtonLaws")
                    "Chemical Bonding" -> navController.navigate("ChemicalBonding")
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6E5175)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(
                    id = when (content.type.name) {
                        "READING" -> R.drawable.book_open_text
                        "SIMULATION" -> R.drawable.waves
                        "VIDEO" -> R.drawable.monitor_play
                        else -> R.drawable.ic_launcher_foreground
                    }
                ),
                contentDescription = "${content.type.name} Icon",
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = content.title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                LinearProgressIndicator(
                    progress = content.progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(6.dp),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.4f)
                )
            }
        }
    }
}