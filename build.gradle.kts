plugins {
    kotlin("jvm") version "2.1.0"
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
