import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-plugin:0.4.7")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.7.21")
}
