plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply("${project.rootDir}/common/android_common.gradle")
android {
    namespace = "com.baseapp.local"
}
dependencies {
    implementation(project(Modules.modelCommon))
    implementation(project(Modules.modelHoroscope))
    implementation(project(Modules.modelPokemon))

    implementation(LibraryAndroid.gson)

    implementation(LibraryAndroid.paging3)

    implementation(LibraryAndroid.roomRuntime)
    annotationProcessor(LibraryAndroid.roomCompiler)
    kapt(LibraryAndroid.roomCompiler)
    implementation(LibraryAndroid.room)
    implementation(LibraryAndroid.roomPaging)
    testImplementation(LibraryAndroid.roomTesting)

    implementation(LibraryAndroid.androidXDataStore)

    // Dagger Hilt
    kaptAndroidTest(LibraryAndroidTesting.dagger)
    // Hilt dependencies
    implementation(LibraryAndroid.daggerHilt)
    kapt(LibraryAndroid.daggerHiltCompiler)
}