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

javafx {
    version = "21.0.9"
    modules = listOf(
        "javafx.base",
        "javafx.controls",
        "javafx.fxml",
        "javafx.graphics"
    )
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
    implementation(libs.guava)
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.0")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set((project.findProperty("mainClass") as String?) ?: "it.unibo.abyssclimber.MainApp")
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events(TestLogEvent.FAILED, TestLogEvent.SKIPPED) 
    }
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
