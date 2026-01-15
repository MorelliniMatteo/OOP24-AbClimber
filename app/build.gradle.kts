import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.api.tasks.JavaExec
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
    // USA QUESTA VERSIONE ESATTA: 8.1.7 è la più stabile per Gradle 8+
    id("io.github.goooler.shadow") version "8.1.7"
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
    // Definizione standard per Gradle moderno
    mainClass.set("it.unibo.abyssclimber.Launcher")
}

// Configurazione Fat JAR
tasks.named<ShadowJar>("shadowJar") {
    archiveBaseName.set("AbyssClimber")
    archiveClassifier.set("")
    archiveVersion.set("1.0")
    
    mergeServiceFiles()
    
    manifest {
        attributes["Main-Class"] = "it.unibo.abyssclimber.Launcher"
    }
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