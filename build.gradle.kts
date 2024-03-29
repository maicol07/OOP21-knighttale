plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    // You can run your app via task "run": ./gradlew run
    application

    /*
     * Adds tasks to export a runnable jar.
     * In order to create it, launch the "shadowJar" task.
     * The runnable jar will be found in build/libs/projectname-all.jar
     */
    id("com.github.johnrengelman.shadow") version "7.0.0"

    // Lint
    checkstyle
    id("com.github.spotbugs") version "5.0.9"
    pmd
}

repositories {
    mavenCentral()
}

val javaFXModules = listOf(
    "base",
    "controls",
    "fxml",
    "media",
    "graphics"
)

val supportedPlatforms = listOf("linux", "mac", "win") // All required for OOP
val jUnitVersion = "5.9.0"
val javaFxVersion = 15

dependencies {
    for (platform in supportedPlatforms) {
        for (module in javaFXModules) {
            implementation("org.openjfx:javafx-$module:$javaFxVersion:$platform")
        }
    }

    implementation("io.github.palexdev:materialfx:11.13.5")
    implementation("net.harawata:appdirs:1.2.1")
    implementation("com.simtechdata:SceneOneFX:1.3.5")
    implementation("io.github.classgraph:classgraph:4.8.149")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("io.vacco.jsonbeans:jsonbeans:1.0.0")
    implementation("com.github.spotbugs:spotbugs-annotations:4.7.2")
    implementation("org.jetbrains:annotations:23.0.0")

    // JUnit API and testing engine
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.testfx:testfx-junit5:4.0.16-alpha")
    testImplementation("org.testfx:openjfx-monocle:jdk-12.0.1+2")
}

application {
    // Define the main class for the application
    mainClass.set("it.unibo.aknightstale.App")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

checkstyle {
    toolVersion = "10.3"
}

pmd {
    ruleSetFiles(files("config/pmd/pmd.xml"))
    ruleSets.clear()
}

spotbugs {
    reportsDir.set(file("$buildDir/reports/spotbugs"))
    excludeFilter.set(file("config/spotbugs/excludes.xml"))
}

tasks.spotbugsMain {
    reports.create("xml") {
        required.set(true)
        outputLocation.set(file("$buildDir/reports/spotbugs/main.xml"))
    }
}

tasks.spotbugsTest {
    reports.create("xml") {
        required.set(true)
        outputLocation.set(file("$buildDir/reports/spotbugs/test.xml"))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    // Enables JUnit 5 Jupiter module
    useJUnitPlatform()
    if (project.hasProperty("headless")) {
        jvmArgs("-Dheadless=true")
    }
}