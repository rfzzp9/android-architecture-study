plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)

    kotlin("kapt")
}

android {
    namespace = "com.csb.koreamoviedb_mvvm"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.csb.koreamoviedb_mvvm"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "SERVICE_KEY", "\"7PT3KTQJJJC189RS9XCZ\"")
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

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.compose.material)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // 네트워크
    implementation(libs.retrofit)
    implementation(libs.retrofit.xml)
    implementation(libs.retrofit.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // 이미지 로드
    implementation(libs.glide.compose)
    implementation(libs.glide.core)
    kapt(libs.glide.compiler)
    implementation(libs.coil.compose)

    // 네비게이션
    implementation(libs.navigation.compose)

    // JSON Serialization
    implementation(libs.kotlinx.serialization.json)

    // 힐트 DI
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // JavaPoet
    implementation(libs.javapoet)
}
