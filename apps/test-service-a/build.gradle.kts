import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    jacoco
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.springBoot)
    alias(libs.plugins.springDependencyManagement)
    alias(libs.plugins.openApiCodegen)
    alias(libs.plugins.kotlinAllOpen)
}

dependencies {
    implementation(libs.kotlinStd)
    runtimeOnly(libs.kotlinReflect)

    implementation(libs.bundles.springBoot)
    implementation(libs.bundles.springSecurity)

    implementation(libs.bundles.persistence)

    implementation(libs.bundles.documentation)

    implementation(libs.bundles.jjwt)

    testImplementation(libs.bundles.test)
    testImplementation(libs.bundles.testContainers)
    testImplementation(libs.bundles.springTesting)
}

tasks.bootJar { enabled = true }

tasks.withType<Test> {
    systemProperty("spring.profiles.active", "test")
    useJUnitPlatform { testLogging { events(TestLogEvent.FAILED) } }
}

testing {
    suites {
        register("konsistTest", JvmTestSuite::class) {
            dependencies {
                implementation(libs.testKonsist)

                // Add 'main' source set dependency
                implementation(project(":apps:test-service-a"))
            }
        }
    }
}

tasks.check {
    dependsOn(tasks.named("detekt"))
    dependsOn(testing.suites.named("konsistTest"))
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
