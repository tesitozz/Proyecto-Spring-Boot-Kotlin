plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.proyecto.proyectoesfrt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.proyecto.proyectoesfrt"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packagingOptions {
        // Excluir archivos de licencia para evitar conflictos
        exclude ("META-INF/LICENSE.md")
        exclude ("META-INF/LICENSE.txt")
        exclude ("META-INF/NOTICE.txt")
        exclude ("META-INF/NOTICE.md")  // Agregar la exclusión para NOTICE.md
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
        sourceCompatibility = JavaVersion.VERSION_17  // Cambiar a Java 17
        targetCompatibility = JavaVersion.VERSION_17  // Cambiar a Java 17
    }

    kotlinOptions {
        jvmTarget = "17"  // Cambiar a JVM target compatible con Java 17
    }

}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Retrofit

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")

    //Jakarta

    implementation ("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation ("org.hibernate:hibernate-core:6.2.15.Final") // Hibernate como proveedor de JPA


    //Fecha
    implementation ("com.google.android.material:material:1.9.0")



}