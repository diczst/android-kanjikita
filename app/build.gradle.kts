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

        buildConfigField("String", "MAIN_ACTIVITY", "${project.findProperty("MAIN_ACTIVITY")}")
        buildConfigField("String", "SEARCH_ACTIVITY", "${project.findProperty("SEARCH_ACTIVITY")}")
        buildConfigField("String", "LEARNED_ACTIVITY", "${project.findProperty("LEARNED_ACTIVITY")}")
        buildConfigField("String", "DETAIL_ACTIVITY", "${project.findProperty("DETAIL_ACTIVITY")}")
        buildConfigField("String", "KANJI_OF_THE_DAY_ACTIVITY", "${project.findProperty("KANJI_OF_THE_DAY_ACTIVITY")}")
        buildConfigField("String", "HIRAKATA_ACTIVITY", "${project.findProperty("HIRAKATA_ACTIVITY")}")
        buildConfigField("String", "DETAIL_ACTIVITY_INTERSTITIAL", "${project.findProperty("DETAIL_ACTIVITY_INTERSTITIAL")}")

        buildFeatures {
            buildConfig = true
        }
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

    // Room dependencies
    implementation ("androidx.room:room-runtime:2.4.3")
    kapt ("androidx.room:room-compiler:2.4.3")
    implementation ("androidx.room:room-ktx:2.4.3")

    // Viewmodel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.3")

    // Coroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Koin dependencies
    implementation ("io.insert-koin:koin-android:3.1.2")

    // Shimmer loading
    implementation ("com.facebook.shimmer:shimmer:0.5.0")
}