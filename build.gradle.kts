import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.1.10"
    id("com.gradleup.shadow") version "8.3.5"
    application
}

group = "de.zvxeb"
version = "1.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin-bundle:6.4.0")
    implementation("org.slf4j:slf4j-simple:2.0.17")
    implementation("org.jcommander:jcommander:2.0")
}

application {
    mainClass.set("MainKt")
}

tasks.withType<ShadowJar> {
    archiveFileName.set("myip2.jar")
}
