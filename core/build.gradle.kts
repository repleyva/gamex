import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.repleyva.core"
    compileSdk = 35

    val properties = Properties()
    properties.load(FileInputStream(rootProject.file("local.properties")))

    defaultConfig {
        minSdk = 26

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
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
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

    api(libs.core.ktx)
    api(libs.bundles.networking)

    api(libs.bundles.lifecycle)

    api(libs.bundles.moshi)
    ksp(libs.moshi.codegen)

    api(libs.hilt.android)
    api(libs.hilt.compose.navigation)
    ksp(libs.hilt.compiler)

    api(libs.bundles.room)
    ksp(libs.room.compiler)

    api(libs.bundles.coil)

    api(libs.bundles.media3)

    api(libs.accompanist)

    api(libs.appcompat)

    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)

    testImplementation(libs.junit)

    debugApi(libs.compose.ui.tooling)
    debugApi(libs.compose.ui.manifest)

    implementation(libs.kotlinx.serialization)

    testImplementation(libs.junit)
    implementation(libs.androidx.ui.test.android)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
    implementation(libs.androidx.navigation.testing)
    testImplementation(libs.truth)
}