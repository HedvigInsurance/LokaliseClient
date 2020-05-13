plugins {
    kotlin("jvm") version "1.3.61"
    id("com.jfrog.bintray") version "1.8.5"
    `maven-publish`
}

val versionName = "1.0.1"

group = "com.hedvig"
version = versionName

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    api("com.squareup.okhttp3:okhttp:4.3.1")
    api("com.fasterxml.jackson.core:jackson-databind:2.11.0")
    api("com.fasterxml.jackson.core:jackson-annotations:2.11.0")
    implementation("org.slf4j:slf4j-api:1.7.30")
}

fun MavenPom.addDependencies() = withXml {
    asNode().appendNode("dependencies").let { depNode ->
        configurations.compile.allDependencies.forEach {
            depNode.appendNode("dependency").apply {
                appendNode("groupId", it.group)
                appendNode("artifactId", it.name)
                appendNode("version", it.version)
            }
        }
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}

bintray {
    user = project.findProperty("bintrayUser").toString()
    key = project.findProperty("bintrayKey").toString()
    publish = true
    setPublications("mavenJava")
    pkg.apply {
        repo = "hedvig-java"
        name = "lokalise-client"
        userOrg = "hedvig"
        setLicenses("MIT")
        vcsUrl = "https://github.com/HedvigInsurance/LokaliseClient.git"
        version.apply {
            name = versionName
            vcsTag = versionName
        }
    }
}