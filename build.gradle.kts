plugins {
    kotlin("jvm") version "2.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `maven-publish`
}

group = "kr.apo2073"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven("https://jitpack.io")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1") {
        exclude("org.bukkit", "bukkit")
    }
    compileOnly("me.clip:placeholderapi:2.11.6")
    implementation("org.bstats:bstats-bukkit:3.0.2")
}

val targetJavaVersion = 17
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.shadowJar {
    archiveFileName.set("aEcon-${version}.jar")
    destinationDirectory=file("C:\\Users\\PC\\Desktop\\Test_Server\\plugins")
    archiveClassifier.set("")
    mergeServiceFiles()
    dependencies {
        exclude(dependency("me.clip:placeholderapi:2.11.6"))
        exclude(dependency("com.github.MilkBowl:VaultAPI:1.7.1"))
        exclude(dependency("org.jetbrains.*:.*"))
    }
    relocate("org.bstats", "kr.apo2073.util")
    minimize()
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
