plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.apprestaurant"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.apprestaurant"
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
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material) // Chỉ giữ 1 dòng
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.viewpager)
    implementation(libs.firebase.database)

    // Firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")
    implementation ("com.google.android.gms:play-services-auth:20.4.0")
    implementation ("com.google.firebase:firebase-firestore:24.0.0")
    // Circle Indicator
    implementation("me.relex:circleindicator:2.1.6")

    implementation ("com.google.firebase:firebase-database")
    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
