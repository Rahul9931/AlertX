import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.vanniktech.maven.publish")
}

android {
    namespace = "com.rahulsaini.alertx"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    coordinates(
        groupId = "io.github.rahul9931",
        artifactId = "alertx",
        version = "2.0.3"
    )

    pom {
        name.set("AlertX")
        description.set("Android alert library")
        url.set("https://github.com/Rahul9931/AlertX")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/license/mit/")
            }
        }

        developers {
            developer {
                id.set("Rahul9931")
                name.set("Rahul Saini")
                email.set("rahulsainigoku@gmail.com")
            }
        }
        
        scm {
            connection.set("scm:git:git://github.com/Rahul9931/AlertX.git")
            developerConnection.set("scm:git:ssh://github.com/Rahul9931/AlertX.git")
            url.set("https://github.com/Rahul9931/AlertX")
        }
    }
}
