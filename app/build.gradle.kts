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

val javafxVersion = "21.0.9"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
    implementation(libs.guava)
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.0")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    runtimeOnly("org.openjfx:javafx-graphics:$javafxVersion:win")
    runtimeOnly("org.openjfx:javafx-base:$javafxVersion:win")
    runtimeOnly("org.openjfx:javafx-controls:$javafxVersion:win")
    runtimeOnly("org.openjfx:javafx-fxml:$javafxVersion:win")
    runtimeOnly("org.openjfx:javafx-graphics:$javafxVersion:linux")
    runtimeOnly("org.openjfx:javafx-base:$javafxVersion:linux")
    runtimeOnly("org.openjfx:javafx-controls:$javafxVersion:linux")
    runtimeOnly("org.openjfx:javafx-fxml:$javafxVersion:linux")
    runtimeOnly("org.openjfx:javafx-graphics:$javafxVersion:mac")
    runtimeOnly("org.openjfx:javafx-base:$javafxVersion:mac")
    runtimeOnly("org.openjfx:javafx-controls:$javafxVersion:mac")
    runtimeOnly("org.openjfx:javafx-fxml:$javafxVersion:mac")
    runtimeOnly("org.openjfx:javafx-graphics:$javafxVersion:mac-aarch64")
    runtimeOnly("org.openjfx:javafx-base:$javafxVersion:mac-aarch64")
    runtimeOnly("org.openjfx:javafx-controls:$javafxVersion:mac-aarch64")
    runtimeOnly("org.openjfx:javafx-fxml:$javafxVersion:mac-aarch64")
    
}

application {
    mainClass.set( "it.unibo.abyssclimber.Launcher")
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events(TestLogEvent.FAILED, TestLogEvent.SKIPPED) 
    }
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE 
    manifest {
        attributes("Main-Class" to "it.unibo.abyssclimber.Launcher") 
    }
    from (
        configurations.runtimeClasspath.get().map { 
            file -> if (file.isDirectory) file else zipTree(file)}
    )
}
