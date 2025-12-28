package pratheekv39.bridgelearn.io.ui.screens.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import pratheekv39.bridgelearn.io.R
import pratheekv39.bridgelearn.io.domain.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSubjectClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val subjects = viewModel.subjects.collectAsState().value
    var showExitDialog by remember { mutableStateOf(false) }
    val activity = LocalContext.current as? Activity
    val isDarkTheme = isSystemInDarkTheme()
    val cardColor = if (isDarkTheme) Color(0xFF6E5175) else Color(0xFFD9BFF6)

    BackHandler { showExitDialog = true }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("QuLearn") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    Text(
                        text = "Hello there",
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 42.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Glad to see you here again!",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 24.sp
                    )
                }
            }

            items(subjects) { subject ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSubjectClick(subject.id) },
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = subject.drawableResId),
                            contentDescription = subject.name,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = subject.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = if (isDarkTheme) Color.White else Color.Black
                            )
                            Text(
                                text = subject.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isDarkTheme) Color.White.copy(alpha = 0.9f)
                                else Color.Black.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
        }

        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                title = { Text("Exit App") },
                text = { Text("Are you sure you want to exit?") },
                confirmButton = {
                    TextButton(onClick = { activity?.finish() }) { Text("Yes") }
                },
                dismissButton = {
                    TextButton(onClick = { showExitDialog = false }) { Text("No") }
                }
            )
        }
    }
}
