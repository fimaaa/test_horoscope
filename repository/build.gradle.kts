plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply("${project.rootDir}/common/android_common.gradle")
android {
    namespace = "com.baseapp.repository"
}

dependencies {
    implementation(project(Modules.common))
    implementation(project(Modules.modelCommon))
    implementation(project(Modules.network))
    implementation(project(Modules.local))
    implementation(project(Modules.modelHoroscope))
    implementation(project(Modules.modelPokemon))

    implementation(LibraryAndroid.gson)

    implementation(LibraryAndroid.okhttpPlatform)
    implementation(LibraryAndroid.okHttp)

    implementation(LibraryAndroid.androidXDataStore)

    // Dagger Hilt
    kaptAndroidTest(LibraryAndroidTesting.dagger)
    // Hilt dependencies
    implementation(LibraryAndroid.daggerHilt)
    kapt(LibraryAndroid.daggerHiltCompiler)

    implementation(LibraryAndroid.paging3)
    implementation(LibraryAndroid.webSocket)

    implementation(LibraryAndroid.geoHash)

//    implementation(platform(LibraryAndroid.firebaseBOM))
//    implementation(LibraryAndroid.firebaseDatabase)
//    implementation(LibraryAndroid.firebaseNotification)
}