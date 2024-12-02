import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.power-assert") version "2.1.0"
}

sourceSets {
    main {
        kotlin.srcDirs("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
    functions = listOf("kotlin.check")
    includedSourceSets = listOf("main")
}
