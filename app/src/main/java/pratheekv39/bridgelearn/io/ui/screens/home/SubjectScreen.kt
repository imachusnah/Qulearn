package pratheekv39.bridgelearn.io.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pratheekv39.bridgelearn.io.R
import androidx.navigation.NavController
import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(
    navController: NavController,
    subjectId: String,
    onNavigateBack: () -> Unit,
    onTopicClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val subject = remember(subjectId) {
        when (subjectId) {
            "Physics" -> Subject("physics", "Physics", "Learn about forces and energy", R.drawable.magnet_straight)
            "Chemistry" -> Subject("chemistry", "Chemistry", "Explore matter and reactions", R.drawable.flask)
            "Biology" -> Subject("biology", "Biology", "Learn about human body and animals", R.drawable.flower)
            "Math" -> Subject("math", "Math", "Explore different types of math tools", R.drawable.math_operations)
            else -> Subject("subject", "Subject", "Subject description", R.drawable.ic_launcher_foreground)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(subject.name) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            // 🔥 LANGSUNG TAMPIL "TOPICS" TANPA OVERVIEW
            item {
                Text(
                    text = "Topics",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // 🔥 LIST TOPIK SEMENTARA (dummy 5 item)
            items(5) { index ->
                TopicCard(
                    title = "Topic ${index + 1}",
                    description = "Tap Here...",
                    onClick = { onTopicClick(index + 1) }
                )
            }
        }
    }
}

@Composable
private fun TopicCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF6E5175))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, color = Color.White)
            Text(text = description, style = MaterialTheme.typography.bodyMedium, color = Color.White)
        }
    }
}

data class Subject(
    val id: String,
    val name: String,
    val description: String,
    val drawableResId: Int
)

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswer: String
)
