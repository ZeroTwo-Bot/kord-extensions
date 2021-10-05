import org.gradle.api.publish.maven.MavenPublication

plugins {
    `maven-publish`
    signing
}

val sourceJar: Task by tasks.getting
val javadocJar: Task by tasks.getting

publishing {
    repositories {
        maven {
            name = "KotDis"

            url = uri("https://nexus.zerotwo.bot/repository/m2-snapshots-public/")
            credentials {
                username = findProperty("nexus.user") as? String
                    ?: System.getenv("NEXUS_USER")
                password = findProperty("nexus.password") as? String
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
