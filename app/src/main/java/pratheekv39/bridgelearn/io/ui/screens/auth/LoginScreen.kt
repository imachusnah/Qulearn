package pratheekv39.bridgelearn.io.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import pratheekv39.bridgelearn.io.data.UserPreferences // <-- Masih kita pakai untuk setLoggedIn
// --- IMPORT BARU UNTUK FIREBASE ---
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val context = LocalContext.current
    // Kita masih pakai UserPreferences untuk menyimpan status login
    val userPrefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()

    // Inisialisasi Firebase Auth
    val auth = Firebase.auth

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 40.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

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
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Email dan password harus diisi"
                    } else {
                        isLoading = true
                        errorMessage = null
                        // --- LOGIKA BARU FIREBASE ---
                        scope.launch {
                            try {
                                // 1. Coba login ke Firebase Auth
                               val result =  auth.signInWithEmailAndPassword(email, password).await()

                                // 2. Jika berhasil, simpan status login ke DataStore
                                userPrefs.setLoggedIn(true)
                                isLoading = false
                                onLoginSuccess() // Navigasi ke Halaman Home

                            } catch (e: Exception) {
                                isLoading = false
                                // Tangani error
                                when (e) {
                                    is FirebaseAuthInvalidUserException -> {
                                        errorMessage = "Email tidak terdaftar."
                                    }
                                    else -> { // (Termasuk password salah)
                                        errorMessage = "Login gagal: Email atau password salah."
                                        Log.e("LoginScreen", "Error: ", e)
                                    }
                                }
                            }
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("LOGIN")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { onNavigateToRegister() }) {
                Text("Haven't Account? Sign up here")
            }
        }
    }
}