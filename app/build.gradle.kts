plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.org.wfnr_2024"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.org.wfnr_2024"
        minSdk = 24
        targetSdk = 34
        versionCode = 26
        versionName = "1.25"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.13.0-alpha02")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.intuit.sdp:sdp-android:1.1.1")
    implementation ("com.intuit.ssp:ssp-android:1.1.1")
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.28")
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-messaging:24.0.0")
    implementation("com.google.android.play:app-update:2.1.0")
    implementation("com.google.android.play:app-update-ktx:2.1.0")
//    // ViewModel
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0-beta01")
//    // ViewModel utilities for Compose
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0-beta01")
//    // LiveData
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0-beta01")
//    // Lifecycles only (without ViewModel or LiveData)
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0-beta01")
//
//    // Saved state module for ViewModel
//    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0")
//
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1-Beta")
//
//    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
   implementation ("com.karumi:dexter:6.2.3")
//    implementation ("androidx.room:room-runtime:2.6.1")
//    implementation ("androidx.room:room-ktx:2.6.1")
//    kapt ("androidx.room:room-compiler:2.6.1")


    // Room components
    implementation ("androidx.room:room-runtime:2.7.0-alpha01")
    annotationProcessor ("androidx.room:room-compiler:2.7.0-alpha01")

    // Optional: Kotlin Extensions and Coroutines support for Room
    implementation ("androidx.room:room-ktx:2.7.0-alpha01")

    // Use KTX extensions to make Room easier to work with Kotlin
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0-rc01")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0-rc01")


        // Room dependencies
        implementation ("androidx.room:room-runtime:2.7.0-alpha01")
        kapt ("androidx.room:room-compiler:2.7.0-alpha01")


    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.14")
    implementation ("com.squareup.retrofit2:converter-scalars:2.11.0")

    //Coroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation ("com.google.code.gson:gson:2.11.0")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0-rc01")
    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0-rc01")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0-rc01")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0-rc01")

    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")





    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.0-rc01")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0-rc01")


    //qrScanner library
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")


    implementation ("com.intuit.ssp:ssp-android:1.1.1")
    implementation ("com.intuit.sdp:sdp-android:1.1.1")

    implementation ("androidx.room:room-runtime:2.7.0-alpha01")
    implementation ("androidx.room:room-ktx:2.7.0-alpha01")
    /*   kapt( "androidx.room:room-compiler:2.2.5")*/

    implementation ("androidx.room:room-runtime:2.7.0-alpha01")
    kapt ("androidx.room:room-compiler:2.7.0-alpha01")


    //kapt ("androidx.room:room-compiler:2.4.0")
    //ksp("androidx.room:room-compiler:2.4.0")

    implementation ("androidx.room:room-runtime:2.7.0-alpha01")
    implementation ("androidx.room:room-ktx:2.7.0-alpha01")
    kapt ("androidx.room:room-compiler:2.7.0-alpha01")

    implementation ("com.karumi:dexter:6.2.3")
    implementation ("androidx.webkit:webkit:1.12.0-alpha01")

    implementation ("com.google.zxing:core:3.5.3")
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")

    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation("androidx.lifecycle:lifecycle-livedata:2.8.0-rc01")

    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation ("com.github.chrisbanes:PhotoView:2.3.0")




}
