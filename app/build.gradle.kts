plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("plugin.serialization") version "1.9.22"

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.refugerestrooms"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.refugerestrooms"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.material)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))

    // FirebaseUI, Database and Play Services Auth
//    implementation(libs.firebase.ui.auth)
//    implementation(libs.play.services.auth)
//    implementation(libs.firebase.database.ktx)
    implementation ("com.firebaseui:firebase-ui-auth:7.2.0")
    implementation ("com.google.android.gms:play-services-auth:20.5.0") //required by email/passwd auth
    implementation("com.google.firebase:firebase-database-ktx:20.2.2")

    // Retrofit
    implementation(libs.retrofit)
    // Retrofit with Kotlin serialization Converter
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)
    // Kotlin serialization
    implementation(libs.kotlinx.serialization.json)

    // OpenStreetMap for Android
    implementation(libs.osmdroid.android)
    //implementation (libs.osm.androd.compose)
    //implementation("tech.utsmankece:osm-android-compose:0.0.5")
    implementation(libs.utsmankece.osm.android.compose)

    // Datastore Preferences
    implementation(libs.androidx.datastore.preferences)

    // Google play services
    implementation(libs.play.services.location)

    // Permissions
    implementation(libs.accompanist.permissions)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}