import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.api.tasks.JavaExec

plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

val javafxVersion = "21.0.9"
val javafxModules = listOf(
        "javafx-base",
        "javafx-controls",
        "javafx-fxml",
        "javafx-graphics"
    )

javafx {
    version = javafxVersion
    modules = listOf(
        "javafx.base",
        "javafx.controls",
        "javafx.fxml",
        "javafx.graphics"
    )
}

val platforms = listOf("win", "linux", "mac", "mac-aarch64")

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.0")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    
    platforms.forEach{ platform -> 
        javafxModules.forEach { module ->
            runtimeOnly("org.openjfx:$module:$javafxVersion:$platform")
        }}
    
}

application {
    mainClass.set( "it.unibo.abyssclimber.Launcher")
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events(TestLogEvent.FAILED, TestLogEvent.SKIPPED, TestLogEvent.PASSED) 
    }
}

tasks.jar {
    archiveFileName = "AbClimber.jar"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE 
    manifest {
        attributes("Main-Class" to "it.unibo.abyssclimber.Launcher") 
    }
    from (
        configurations.runtimeClasspath.get().map { 
            file -> if (file.isDirectory) file else zipTree(file)}
    )
}

tasks.build {
    dependsOn("jar")
}
