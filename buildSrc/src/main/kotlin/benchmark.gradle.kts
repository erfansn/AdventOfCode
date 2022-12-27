import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension

plugins {
    java
    kotlin("plugin.allopen")
    id("org.jetbrains.kotlinx.benchmark")
}

repositories {
    mavenCentral()
}

configure<AllOpenExtension> {
    annotation("org.openjdk.jmh.annotations.State")
}

sourceSets.create("benchmark")
benchmark {
    targets {
        register("benchmark")
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.7")
}
