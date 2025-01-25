plugins {
    /*
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "2.0.21"
    */

    id("com.android.application")
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.pmdm.pokemonagh"
    compileSdk = 35



    defaultConfig {
        applicationId = "com.pmdm.pokemonagh"
        minSdk = 24
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
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //implementation(libs.navigation.runtime)

    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.8.5")
    implementation("androidx.navigation:navigation-dynamic-features-runtime:2.8.5")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:2.8.5")

    // JSON serialization library, works with the Kotlin serialization plugin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    //implementation(libs.preference)
    implementation("androidx.preference:preference:1.2.1")
    implementation("androidx.preference:preference-ktx:1.2.1")

    implementation("androidx.navigation:navigation-ui:2.8.5")
    //Fragments
    implementation("androidx.fragment:fragment:1.8.5")
    implementation("androidx.fragment:fragment-ktx:1.8.5")
    implementation("androidx.fragment:fragment-compose:1.8.5")

    implementation("androidx.navigation:navigation-fragment:2.8.5")

    //RecyclerView y CardView
    implementation("androidx.recyclerview:recyclerview")
    implementation("androidx.cardview:cardview")

    //Librería Picasso
    implementation ("com.squareup.picasso:picasso:2.71828")

    //FirebaseAuth
    implementation ("com.firebaseui:firebase-ui-auth:7.2.0")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")

    // Declare the dependency for the Cloud Firestore library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-firestore")
    //implementation("com.google.firebase:firebase-admin:1.32.0")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.4")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Retrofit with Scalar Converter
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

}