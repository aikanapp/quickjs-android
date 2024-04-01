import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.jetbrains.kotlin.cli.jvm.main
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import java.util.zip.CRC32

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.hippo.quickjs.test.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hippo.quickjs.test.android"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                arguments+="-DCMAKE_VERBOSE_MAKEFILE:BOOL=ON"
            }
        }
    }

    sourceSets {
        getByName("main") {
            assets.srcDirs("${projectDir}/testassets")
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
    externalNativeBuild {
        cmake {
            path = file("CMakeLists.txt")
        }
    }
}

tasks.register<Zip>("bundleTestAssets") {
    doFirst {
        delete("${projectDir}/testassets")
        file("${projectDir}/testassets").mkdirs()
    }

    from("${rootProject.projectDir}/quickjs/quickjs")
    exclude("**/*.c", "**/*.h")
    archivesName = "testassets"
    destinationDirectory = file("${projectDir}/testassets")

    doLast {
        val testassets = file("${projectDir}/testassets/testassets.zip")
        val crc32 = CRC32()
        testassets.forEachBlock(4096) { buffer, bytesRead ->
            crc32.update(buffer, 0, bytesRead)
        }
        file("${projectDir}/testassets/testassets-${crc32.value}.crc32").createNewFile()
    }
}

project.afterEvaluate {
    tasks.named("generateDebugAssets")?.dependsOn("bundleTestAssets")
    tasks.named("generateReleaseAssets")?.dependsOn("bundleTestAssets")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.relinker)
    implementation(libs.zip4j)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}