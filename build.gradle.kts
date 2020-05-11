plugins {
    kotlin("jvm") version "1.3.61"
    id("com.jfrog.bintray") version "1.8.5"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.squareup.okhttp3:okhttp:4.3.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.11.0")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

bintray {
    user = project.findProperty("bintrayUser").toString()
    key = project.findProperty("bintrayKey").toString()
    pkg.apply {
        repo = "hedvig-java"
        name = "lokalise-client"
        userOrg = "hedvig"
        setLicenses("MIT")
        vcsUrl = "https://github.com/HedvigInsurance/LokaliseClient.git"
        version.apply {
            name = "0.0.0"
            desc = "Test version"
            released  = "Mon May 11 16:59:50 CEST 2020"
            vcsTag = "0.0.0"
        }
    }
}