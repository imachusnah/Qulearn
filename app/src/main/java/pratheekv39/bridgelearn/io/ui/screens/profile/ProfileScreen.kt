@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import pratheekv39.bridgelearn.io.R

// --- IMPORT VIEWMODEL & DATA CLASS ---
import pratheekv39.bridgelearn.io.domain.viewmodels.ProfileViewModel
import pratheekv39.bridgelearn.io.domain.viewmodels.ProfileViewModelFactory
import pratheekv39.bridgelearn.io.domain.viewmodels.UserProfile
import pratheekv39.bridgelearn.io.domain.viewmodels.UserPreferences

/**
 * Full ProfileScreen.kt
 *
 * - ProfileScreen
 * - ProfileHeader (edit & delete buttons side-by-side)
 * - SettingsSection with purple card background
 * - SettingItem
 * - Adaptive colors for light/dark mode:
 *     - Buttons and icons adapt to MaterialTheme colorScheme
 *     - Switch thumb is white (dark mode) or black (light mode)
 *     - Switch track is transparent (so background shows through)
 * - History button (purple)
 * - Logout button (red)
 *
 * Paste this file to replace your existing ProfileScreen.kt
 */

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(LocalContext.current.applicationContext as Application)
    ),
    darkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit,
    onLogout: () -> Unit
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val profileImageUri by viewModel.profileImageUri.collectAsState()

    val updateProfileImage = { uri: Uri? -> viewModel.updateProfileImage(uri) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ProfileHeader(
                profile = userProfile,
                onImageSelected = updateProfileImage,
                profileImageUri = profileImageUri,
                darkMode = darkMode
            )
        }

        // HISTORY BUTTON (ungu)
        item {
            // Use primary color of theme as purple / or override with fixed purple
            val historyColor = MaterialTheme.colorScheme.primary
            Button(
                onClick = { navController.navigate("history") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6C4F74) ,
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.History, contentDescription = "History")
                    Spacer(Modifier.width(8.dp))
                    Text("See History")
                }
            }
        }

        // SETTINGS CARD (ungu background)
        item {
            SettingsSection(
                preferences = userProfile.preferences,
                darkMode = darkMode,
                onDarkModeChanged = onDarkModeChanged
            )
        }

        // LOGOUT BUTTON (merah)
        item {
            val logoutColor = MaterialTheme.colorScheme.error
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF701313),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Logout, contentDescription = "Logout")
                    Spacer(Modifier.width(8.dp))
                    Text("Logout")
                }
            }
        }
    }
}

/* ===========================
   Profile header + action buttons
   - Edit & Delete are side-by-side
   - Colors adapt to dark/light mode
   =========================== */
@Composable
fun ProfileHeader(
    profile: UserProfile,
    onImageSelected: (Uri?) -> Unit,
    profileImageUri: Uri?,
    darkMode: Boolean
) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> if (uri != null) onImageSelected(uri) }

    // Primary color (purple) and error (red) come from theme so they adapt automatically
    val primaryColor = MaterialTheme.colorScheme.primary
    val errorColor = MaterialTheme.colorScheme.error
    val contentColorForButtons = if (darkMode) Color.White else primaryColor
    val deleteContentColorForButtons = if (darkMode) Color.White else errorColor

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile avatar circle
        Surface(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .clickable { imagePickerLauncher.launch("image/*") },
            color = primaryColor
        ) {
            if (profileImageUri != null) {
                AsyncImage(
                    model = profileImageUri,
                    contentDescription = "Profile Picture",
                    placeholder = painterResource(R.drawable.profil3),
                    error = painterResource(R.drawable.profil3),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(profile.name, style = MaterialTheme.typography.headlineMedium)
        Text(
            profile.grade,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (profile.bio.isNotEmpty()) {
            Text(
                profile.bio,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        // Buttons side-by-side
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Edit button (outlined)
            OutlinedButton(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = contentColorForButtons
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = SolidColor(contentColorForButtons)
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = contentColorForButtons)
                Spacer(Modifier.width(8.dp))
                Text("Edit Foto", color = contentColorForButtons)
            }

            Spacer(Modifier.width(12.dp))

            // Delete button (outlined)
            OutlinedButton(
                onClick = { onImageSelected(null) },
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = deleteContentColorForButtons
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = SolidColor(deleteContentColorForButtons)
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = deleteContentColorForButtons)
                Spacer(Modifier.width(8.dp))
                Text("Hapus", color = deleteContentColorForButtons)
            }
        }
    }
}

/* ===========================
   Settings section with purple card background
   - Dark mode switch: thumb white in dark mode, black in light mode
   - Track transparent to show the purple card behind it
   =========================== */
@Composable
fun SettingsSection(
    preferences: UserPreferences,
    darkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit
) {
    Text("Settings", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 8.dp))

    // Purple background card for settings area (keeps same look as requested)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6E5175), // fixed purple as requested
            contentColor = Color.White
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SettingItem(
                title = "Dark Mode",
                icon = Icons.Default.DarkMode
            ) {
                // Switch styling: thumb white in dark mode, black in light mode; track transparent
                val thumbColor = if (darkMode) Color.White else Color.Black

                Switch(
                    checked = darkMode,
                    onCheckedChange = { onDarkModeChanged(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Black,        // thumb putih saat ON
                        uncheckedThumbColor = Color.White,      // thumb putih saat OFF
                        checkedTrackColor = Color.White,  // track transparan
                        uncheckedTrackColor = Color.Black // track transparan
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            SettingItem(
                title = "Language",
                icon = Icons.Default.Language
            ) {
                Text(preferences.language, color = Color.White)
            }
        }
    }
}

/* ===========================
   Setting item row
   - Icon and label on left, trailing composable on right
   =========================== */
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = title, tint = Color.White)
            Spacer(Modifier.width(16.dp))
            Text(title, color = Color.White)
        }
        trailing()
    }
}
