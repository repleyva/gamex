import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.repleyva.gamexapp"
    compileSdk = 35

    val properties = Properties()
    properties.load(FileInputStream(rootProject.file("local.properties")))

    defaultConfig {
        applicationId = "com.repleyva.gamexapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }

        buildConfigField("String", "BASE_URL", "\"https://api.rawg.io/api/\"")
        buildConfigField("String", "API_KEY", "\"${properties["API_KEY"]}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
        layout.buildDirectory.dir("compose_compiler")
        rootProject.layout.projectDirectory.file("stability_config.conf")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
    kotlin {
        sourceSets {
            debug {
                kotlin.srcDir("build/generated/ksp/debug/kotlin")
            }
            release {
                kotlin.srcDir("build/generated/ksp/release/kotlin")
            }
        }
    }
}

dependencies {
    implementation(project(":core"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.androidx.ui.test.android)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.coroutines.test)
    implementation(libs.androidx.navigation.testing)
    testImplementation(libs.truth)
    androidTestImplementation(libs.truth)

    implementation(libs.kotlinx.serialization)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}