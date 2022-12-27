plugins {
    kotlin("jvm") version "1.7.21"
    benchmark
}

tasks {
    wrapper {
        gradleVersion = "7.6"
    }
}

dependencies {
    benchmarkImplementation(with(sourceSets.main.get()) { output + runtimeClasspath })
}
