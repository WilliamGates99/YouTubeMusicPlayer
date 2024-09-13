import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

val properties = gradleLocalProperties(rootDir, providers)

android {
    namespace = "com.xeniac.youtubemusicplayer"
    compileSdk = 35
    buildToolsVersion = "35.0.0"

    defaultConfig {
        applicationId = "com.xeniac.youtubemusicplayer"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(path = properties.getProperty("KEY_STORE_PATH"))
            storePassword = properties.getProperty("KEY_STORE_PASSWORD")
            keyAlias = properties.getProperty("KEY_ALIAS")
            keyPassword = properties.getProperty("KEY_PASSWORD")
        }
    }

    buildTypes {
        debug {
            versionNameSuffix = " - Debug"
            applicationIdSuffix = ".debug"

            resValue(
                type = "color",
                name = "appIconBackground",
                value = "#FFFF9100" // Orange
            )
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("release")

            resValue(
                type = "color",
                name = "appIconBackground",
                value = "#FF0E0E0E" // Dark Gray
            )
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        // Java 8+ API Desugaring Support
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }

    kotlinOptions {
        jvmTarget = "22"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

hilt {
    enableAggregatingTask = true
}

dependencies {
    // Java 8+ API Desugaring Support
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.core.splashscreen)
    implementation(libs.kotlinx.serialization.json) // Kotlin JSON Serialization Library

    // Jetpack Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3) // Material Design 3
    implementation(libs.compose.runtime.livedata) // Compose Integration with LiveData
    implementation(libs.compose.ui.tooling.preview) // Android Studio Compose Preview Support
    debugImplementation(libs.compose.ui.tooling) // Android Studio Compose Preview Support
    implementation(libs.activity.compose) // Compose Integration with Activities
    implementation(libs.constraintlayout.compose) // Compose Constraint Layout
    implementation(libs.navigation.compose) // Compose Navigation
    implementation(libs.hilt.navigation.compose) // Compose Navigation Integration with Hilt

    // Dagger - Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Architectural Components
    implementation(libs.lifecycle.viewmodel.ktx) // ViewModel
    implementation(libs.lifecycle.viewmodel.compose) // ViewModel Utilities for Compose
    implementation(libs.lifecycle.runtime.ktx) // Lifecycles Only (without ViewModel or LiveData)
    implementation(libs.lifecycle.runtime.compose) // Lifecycle Utilities for Compose

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Timber Library
    implementation(libs.timber)

    // YouTube Player Library
    implementation(libs.youtubeplayer)
}