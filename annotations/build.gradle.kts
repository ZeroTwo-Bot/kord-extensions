import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream
import java.net.URL

plugins {
    `maven-publish`
    signing

    kotlin("jvm")

    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.dokka")
}

dependencies {
    implementation(libs.kotlin.stdlib)

    detektPlugins(libs.detekt)
}

val sourceJar = task("sourceJar", Jar::class) {
    dependsOn(tasks["classes"])
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar = task("javadocJar", Jar::class) {
    dependsOn("dokkaJavadoc")
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    explicitApi()
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

detekt {
    buildUponDefaultConfig = true
    config = files("$rootDir/detekt.yml")

    autoCorrect = true
}

publishing {
    repositories {
        maven {
            name = "KotDis"

            url = uri("https://nexus.zerotwo.bot/repository/m2-snapshots-public/")

            credentials {
                username = project.findProperty("nexus.user") as String?
                    ?: System.getenv("NEXUS_USER")

                password = project.findProperty("nexus.password") as String?
                    ?: System.getenv("NEXUS_PASSWORD")
            }

            version = project.version
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components.getByName("java"))

            artifact(sourceJar)
            artifact(javadocJar)
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["maven"])
}

fun runCommand(command: String): String {
    val output = ByteArrayOutputStream()

    project.exec {
        commandLine(command.split(" "))
        standardOutput = output
    }

    return output.toString().trim()
}

fun getCurrentGitBranch(): String {  // https://gist.github.com/lordcodes/15b2a4aecbeff7c3238a70bfd20f0931
    var gitBranch = "Unknown branch"

    try {
        gitBranch = runCommand("git rev-parse --abbrev-ref HEAD")
    } catch (t: Throwable) {
        println(t)
    }

    return gitBranch
}

tasks.dokkaHtml.configure {
    moduleName.set("Kord Extensions: Annotation Processor")

    dokkaSourceSets {
        configureEach {
            includeNonPublic.set(false)
            skipDeprecated.set(false)

            displayName.set("Kord Extensions: Java Time")
            includes.from("packages.md")
            jdkVersion.set(8)

            sourceLink {
                localDirectory.set(file("${project.projectDir}/src/main/kotlin"))

                remoteUrl.set(
                    URL(
                        "https://github.com/Kotlin-Discord/kord-extensions/" +
                            "tree/${getCurrentGitBranch()}/annotation-processor/src/main/kotlin"
                    )
                )

                remoteLineSuffix.set("#L")
            }

            externalDocumentationLink {
                url.set(URL("http://kordlib.github.io/kord/common/common/"))
            }

            externalDocumentationLink {
                url.set(URL("http://kordlib.github.io/kord/core/core/"))
            }
        }
    }
}

tasks.build {
    this.finalizedBy(sourceJar, javadocJar)
}
