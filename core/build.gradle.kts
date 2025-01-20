import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
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

    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material)
    api(libs.androidx.material3)
    api(libs.androidx.ui.test.android)
    api(libs.androidx.runtime.livedata)
    api(libs.androidx.appcompat)
    api(libs.androidx.constraintlayout)
    api(libs.androidx.navigation.testing)
    api(libs.hilt.android.testing)

    debugApi(libs.androidx.ui.tooling)
    debugApi(libs.androidx.ui.test.manifest)

    api(libs.androidx.core.splashscreen)
    api(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    api(libs.androidx.hilt.navigation.compose)
    api(libs.retrofit)
    api(libs.retrofit.gson)
    api(libs.coil.compose)
    api(libs.datastore.preferences)
    api(libs.compose.foundation)
    api(libs.kotlinx.serialization)
    api(libs.paging.runtime)
    api(libs.paging.compose)
    api(libs.room.runtime)
    ksp(libs.room.compiler)
    api(libs.room.ktx)
    api(libs.okhttp)
    api(libs.okhttp.logging.interceptor)

    api(platform(libs.firebase.bom))
    api(libs.firebase.analytics)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.chuck.debug)
    releaseImplementation(libs.chuck.release)
}