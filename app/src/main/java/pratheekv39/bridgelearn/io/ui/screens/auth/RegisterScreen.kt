package pratheekv39.bridgelearn.io.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
// --- IMPORT BARU UNTUK FIREBASE ---
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pratheekv39.bridgelearn.io.domain.viewmodels.UserProfile // <-- Import 'cetakan' profil
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.tasks.await

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    // Kita TIDAK PERLU UserPreferences di sini lagi
    val scope = rememberCoroutineScope()

    // Inisialisasi Firebase Auth & Firestore
    val auth = Firebase.auth
    val db = Firebase.firestore

    // State untuk input form
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") } // <-- TAMBAHKAN INPUT BARU UNTUK KELAS
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // State untuk error
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "SIGN UP",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 40.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- INPUT BARU UNTUK 'GRADE' (KELAS) ---
            OutlinedTextField(
                value = grade,
                onValueChange = { grade = it },
                label = { Text("Class (e.g., 7th Grade, Kelas 10)") }, // Contoh: "Kelas 10"
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            // ----------------------------------------

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                // ... (sisa kode password field tidak berubah)
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                // ... (sisa kode confirm password field tidak berubah)
                label = { Text("Confirm Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                }
            )

            // Tampilkan pesan error dari Firebase
            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Validasi input
                    if (name.isBlank() || email.isBlank() || grade.isBlank() || password.isBlank()) {
                        errorMessage = "Semua field harus diisi"
                    } else if (password != confirmPassword) {
                        errorMessage = "Password tidak cocok!"
                    } else {
                        isLoading = true
                        errorMessage = null
                        // --- LOGIKA BARU FIREBASE ---
                        scope.launch {
                            try {
                                // 1. Buat akun di Firebase Auth
                                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                                val firebaseUser = authResult.user

                                if (firebaseUser != null) {
                                    // 2. Buat objek UserProfile
                                    val newUserProfile = UserProfile(
                                        id = firebaseUser.uid,
                                        name = name,
                                        email = email,
                                        grade = grade
                                    )

                                    // 3. Simpan profil ke Firestore
                                    db.collection("users")
                                        .document(firebaseUser.uid) // Gunakan UID sebagai ID dokumen
                                        .set(newUserProfile)
                                        .await() // Tunggu sampai selesai

                                    Log.d("RegisterScreen", "Profil user berhasil disimpan ke Firestore!")
                                    isLoading = false
                                    onRegisterSuccess() // Navigasi ke Halaman Login/Home
                                }
                            } catch (e: Exception) {
                                isLoading = false
                                // Tangani error Firebase
                                when (e) {
                                    is FirebaseAuthUserCollisionException -> {
                                        errorMessage = "Email ini sudah terdaftar."
                                    }
                                    is FirebaseAuthInvalidCredentialsException -> {
                                        errorMessage = "Format email salah."
                                    }
                                    else -> {
                                        errorMessage = "Registrasi gagal: ${e.message}"
                                        Log.e("RegisterScreen", "Error: ", e)
                                    }
                                }
                            }
                        }
                    }
                },
                enabled = !isLoading, // Tombol nonaktif saat loading
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Sign Up")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { onNavigateToLogin() }) {
                Text("Have account? Login")
            }
        }
    }
}