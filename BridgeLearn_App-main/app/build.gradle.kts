plugins {
    id("com.google.gms.google-services")
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "pratheekv39.bridgelearn.io"
    compileSdk = 34

    defaultConfig {
        applicationId = "pratheekv39.bridgelearn.io"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
dependencies {
    // 🔹 Compose BOM (kelola versi Compose otomatis)
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // ✅ Firebase (Menggunakan 'libs' dari libs.versions.toml)
    implementation(platform(libs.firebase.bom)) // <- Gunakan alias BOM
    implementation("com.google.firebase:firebase-analytics-ktx") // <- Biarkan ini jika belum ada di libs
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation("com.google.firebase:firebase-database-ktx") // <- Biarkan ini jika belum ada di libs
    implementation("com.google.firebase:firebase-storage-ktx") // <- Biarkan ini jika belum ada di libs

    // ✅ Coroutines (untuk .await())
    implementation(libs.kotlinx.coroutines.play.services)

    // 🔹 Jetpack Compose Essentials
    implementation("androidx.compose.animation:animation")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // 🔹 Gambar & JSON

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp) // <-- TAMBAHKAN BARIS INI
    implementation(libs.gson)
    implementation("io.coil-kt:coil-compose:2.4.0")

    // 🔹 Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}