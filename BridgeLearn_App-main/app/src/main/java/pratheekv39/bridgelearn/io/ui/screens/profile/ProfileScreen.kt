package pratheekv39.bridgelearn.io.ui.screens.profile

// --- IMPORT UNTUK COMPOSE & NAVIGASI ---
import android.app.Application
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext // <-- IMPORT DIPERLUKAN
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel // <-- IMPORT DIPERLUKAN
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pratheekv39.bridgelearn.io.R

// --- IMPORT VIEWMODEL & DATA CLASS DARI LOKASI BARU ---
import pratheekv39.bridgelearn.io.domain.viewmodels.Achievement
import pratheekv39.bridgelearn.io.domain.viewmodels.ProfileViewModel
import pratheekv39.bridgelearn.io.domain.viewmodels.ProfileViewModelFactory
import pratheekv39.bridgelearn.io.domain.viewmodels.UserProfile
import pratheekv39.bridgelearn.io.domain.viewmodels.UserPreferences


// =====================================================================
// FUNGSI UTAMA PROFILE SCREEN
// =====================================================================

@Composable
fun ProfileScreen(
    navController: NavController,
    // --- PANGGIL VIEWMODEL MENGGUNAKAN FACTORY ---
    viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    ),
    darkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit,
    onLogout: () -> Unit // <-- Parameter sudah ditambahkan
) {

    // Ambil data dari ViewModel. UI akan update otomatis.
    val userProfile by viewModel.userProfile.collectAsState()
    val profileImageUri by viewModel.profileImageUri.collectAsState()

    val updateProfileImage = { uri: Uri? ->
        viewModel.updateProfileImage(uri)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Profile Header
        item {
            ProfileHeader(
                profile = userProfile, // <-- Gunakan data dari ViewModel
                onImageSelected = updateProfileImage,
                profileImageUri = profileImageUri
            )
        }

        // Stats Section
        item {
            StatsSection(userProfile) // <-- Gunakan data dari ViewModel
        }

        // Achievements Section
        item {
            Text(
                text = "Achievements",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        item {
            AchievementsSection(viewModel.achievements) // <-- Gunakan data dari ViewModel
        }

        // Tombol Riwayat Kuis
        item {
            Button(
                onClick = {
                    navController.navigate("history")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                // Atur warna tombol jadi Biru
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.History, contentDescription = "History")
                    Spacer(Modifier.width(8.dp))
                    Text("Lihat Riwayat Kuis")
                }
            }
        }

        // --- KODE BARU UNTUK TOMBOL LOGOUT ---
        item {
            Button(
                onClick = onLogout, // <-- Memanggil fungsi logout dari NavGraph
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error, // Warna merah
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Logout, contentDescription = "Logout")
                    Spacer(Modifier.width(8.dp))
                    Text("Logout")
                }
            }
        }
        // --- BATAS AKHIR KODE BARU ---

        // Settings Section
        item {
            SettingsSection(
                preferences = userProfile.preferences, // <-- Gunakan data dari ViewModel
                darkMode = darkMode,
                onDarkModeChanged = onDarkModeChanged
            )
        }
    }
}


@Composable
fun ProfileHeader(
    profile: UserProfile,
    onImageSelected: (Uri?) -> Unit,
    profileImageUri: Uri?
) {
    var isImageZoomed by remember { mutableStateOf(false) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            onImageSelected(uri) // Pass the URI back to the parent
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Image
        Surface(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .clickable { isImageZoomed = true },
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            if (profileImageUri != null) {
                AsyncImage(
                    model = profileImageUri,
                    contentDescription = "Profile Picture",
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    error = painterResource(R.drawable.ic_launcher_foreground),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Default Profile Picture",
                    tint = Color.White,
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = profile.name, // <-- Akan menampilkan "Loading..." lalu nama
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = profile.grade,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (profile.bio.isNotEmpty()) {
            Text(
                text = profile.bio,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (isImageZoomed) {
            Dialog(
                onDismissRequest = { isImageZoomed = false }
            ) {
                Box (
                    modifier = Modifier
                        .size(400.dp)
                        .background(Color.Black.copy(alpha = 0.3f))
                        .clip(RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(250.dp),
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize(),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            if (profileImageUri != null) {
                                AsyncImage(
                                    model = profileImageUri,
                                    contentDescription = "Profile Picture",
                                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                                    error = painterResource(R.drawable.ic_launcher_foreground),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                            } else {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(32.dp)
                                        .fillMaxSize()
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        IconButton(
                            onClick = { isImageZoomed = false },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }

        // Edit Profile Button
        OutlinedButton(
            onClick = { imagePickerLauncher.launch("image/*") },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Icon(Icons.Default.Edit, contentDescription = null, tint = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Edit Profile", style = TextStyle(color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium))
        }
    }
}

@Composable
fun StatsSection(profile: UserProfile) {
    Text(
        text = "Learning Stats",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatItem(
                    value = profile.completedLessons.toString(),
                    label = "Lessons",
                    icon = Icons.Default.School,
                )
                StatItem(
                    value = profile.quizzesTaken.toString(),
                    label = "Quizzes",
                    icon = Icons.Default.Quiz
                )
                StatItem(
                    value = "${(profile.averageScore * 100).toInt()}%",
                    label = "Avg. Score",
                    icon = Icons.Default.Grade
                )
            }
        }
    }
}

@Composable
fun StatItem(
    value: String,
    label: String,
    icon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
    }
}

@Composable
fun AchievementsSection(achievements: List<Achievement>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            achievements.forEach { achievement ->
                AchievementCard(
                    achievement = achievement,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun AchievementCard(
    achievement: Achievement,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Panggil fungsi Composable 'icon'
            achievement.icon()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = achievement.title,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
            LinearProgressIndicator(
                progress = achievement.progress,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun SettingsSection(
    preferences: UserPreferences,
    darkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit
) {
    Text(
        text = "Settings",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Theme Setting
            SettingItem(
                title = "Dark Mode",
                icon = Icons.Default.DarkMode,
                {
                    Switch(
                        checked = darkMode,
                        onCheckedChange = { isChecked -> onDarkModeChanged(isChecked) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.surfaceVariant,
                            uncheckedThumbColor = MaterialTheme.colorScheme.background,
                            checkedTrackColor = MaterialTheme.colorScheme.background,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                            checkedBorderColor = MaterialTheme.colorScheme.background,
                            uncheckedBorderColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
            )

            // Notifications Setting
            SettingItem(
                title = "Notifications",
                icon = Icons.Default.Notifications,
                trailing = {
                    Switch(
                        checked = preferences.notificationsEnabled,
                        onCheckedChange = { /* TODO: Implement notifications toggle */ },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.surfaceVariant,
                            uncheckedThumbColor = MaterialTheme.colorScheme.background,
                            checkedTrackColor = MaterialTheme.colorScheme.background,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                            checkedBorderColor = MaterialTheme.colorScheme.background,
                            uncheckedBorderColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Language Setting (TODO: Implement)
            SettingItem(
                title = "Language",
                icon = Icons.Default.Language,
                trailing = {
                    Text(preferences.language, color = Color.White)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Accessibility Options (TODO: Implement)
            SettingItem(
                title = "Accessibility",
                icon = Icons.Default.Accessibility,
                trailing = {
                    Icon(Icons.Default.ChevronRight, null)
                }
            )
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    icon: ImageVector,
    trailing: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, color = Color.White)
        }
        trailing()
    }
}