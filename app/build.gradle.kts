plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.multuscalendrius"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.multuscalendrius"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    packaging {
        resources.pickFirsts.add("META-INF/INDEX.LIST")
        resources.pickFirsts.add("META-INF/DEPENDENCIES")
        resources.pickFirsts.add("META-INF/io.netty.versions.properties")
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.datatype.jsr310)
    implementation(libs.okhttp)
    implementation(libs.ambilwarna)
    implementation(libs.firebase.appdistribution.gradle)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}