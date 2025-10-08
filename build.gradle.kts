plugins {
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "8.3.0"
}

group = "com.github.drzoddiak"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Dependency to handle CSV formatting
    implementation("com.jsoizo:kotlin-csv-jvm:1.10.0")
}

kotlin {
    jvmToolchain(21)
}

// Defines the main class when running the jar
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.github.drzoddiak.DCBudgetKt"
    }
}

tasks.jar {
    // Don't produce a 'thin jar'
    this.enabled = false
    // Ensures a fatjar is made when built
    dependsOn(tasks.shadowJar)
}