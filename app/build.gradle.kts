plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose) // ok com Kotlin 2.0.21
    alias(libs.plugins.ksp)            // KSP para Room
    id("kotlin-parcelize")
}

android {
    namespace = "br.com.fernandodev.hinovaoficinas"

    // Se o Android 16/preview (API 36) não estiver instalado, troque temporariamente para 35
    compileSdk = 36

    defaultConfig {
        applicationId = "br.com.fernandodev.hinovaoficinas"
        minSdk = 24
        targetSdk = 36 // pode usar 35 para estabilidade se necessário
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Placeholder para chave do Maps (se usar)
        manifestPlaceholders["MAPS_API_KEY"] =
            (project.findProperty("MAPS_API_KEY") as String? ?: "")
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

    buildFeatures {
        viewBinding = true
        compose = true
    }
    // Com o plugin kotlin.compose, NÃO defina kotlinCompilerExtensionVersion manualmente.

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // --- Compose (usando BOM do catálogo) ---
    implementation(libs.androidx.core.ktx)                     // alias de androidx-core-ktx
    implementation(libs.androidx.lifecycle.runtime.ktx)        // alias de androidx-lifecycle-runtime-ktx
    implementation(libs.androidx.activity.compose)             // alias de androidx-activity-compose
    implementation(platform(libs.androidx.compose.bom))        // alias de androidx-compose-bom
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)           // alias de androidx-navigation-compose
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // --- Views / Navegação por abas / Lista ---
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.activity:activity-ktx:1.9.2")
    implementation("androidx.fragment:fragment-ktx:1.8.2")

    // --- Sessão + Coroutines + Gson ---
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    implementation("com.google.code.gson:gson:2.11.0")

    // --- Rede (Retrofit + OkHttp) ---
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // --- Room (com KSP) ---
    implementation(libs.room.runtime)      // alias de room-runtime
    implementation(libs.room.ktx)          // alias de room-ktx
    ksp(libs.room.compiler)                // alias de room-compiler

    // --- Koin (DI) ---
    implementation(libs.koin.android)          // alias de koin-android
    implementation(libs.koin.androidx.compose) // alias de koin-androidx-compose

    // --- ZXing (QRCode) ---
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.5.3")

    // --- Testes ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// KSP: Room + logs verbosos para diagnóstico
ksp {
    arg("ksp.verbose", "true")
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
}
