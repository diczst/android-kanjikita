plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")

}

android {
    namespace = "com.neonusa.belajarkanjijlpt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.neonusa.belajarkanjijlpt"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // untuk deserialisasi json
    implementation ("com.google.code.gson:gson:2.8.6")

    // Kotpref
    implementation ("com.chibatching.kotpref:kotpref:2.13.1")

    // Google Admob
    implementation ("com.google.android.gms:play-services-ads:21.1.0")

    // koin
    implementation ("io.insert-koin:koin-core:2.2.2")
    implementation ("io.insert-koin:koin-androidx-viewmodel:2.2.2")

    //room
    implementation("androidx.room:room-runtime:2.5.0-alpha02")
    kapt("androidx.room:room-compiler:2.5.0-alpha02")

    // Google Admob
//    implementation ("com.google.android.gms:play-services-ads:21.1.0")
}