import com.vroff.buildsrc.BuildConfigs
import com.vroff.buildsrc.KeysApiDebug
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.vroff.network"
    compileSdk = BuildConfigs.COMPILE_SDK

    defaultConfig {
        minSdk = BuildConfigs.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildTypes {
        debug {
            buildConfigField(
                "String",
                "STREAMING_AVAILABILITY_BASE_URL",
                KeysApiDebug.STREAMING_AVAILABILITY_BASE_URL,
            )
            buildConfigField(
                "String",
                "STREAMING_AVAILABILITY_API_KEY",
                KeysApiDebug.STREAMING_AVAILABILITY_API_KEY,
            )

            buildConfigField(
                "String",
                "STREAMING_AVAILABILITY_API_HOST",
                KeysApiDebug.STREAMING_AVAILABILITY_API_HOST,
            )

            buildConfigField(
                "String",
                "TMDB_API_KEY",
                KeysApiDebug.TMBD_API_KEY,
            )

            buildConfigField(
                "String",
                "TMDB_API_HOST",
                KeysApiDebug.TMBD_API_HOST,
            )
        }
    }
}

dependencies {
    implementation(libs.coil3.coil.network.okhttp)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.paging.common)
    ksp(libs.hilt.android.compiler)
    implementation(project(":core:domain"))
}
