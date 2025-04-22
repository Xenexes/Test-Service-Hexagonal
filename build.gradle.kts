import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  idea
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.detekt)
  alias(libs.plugins.ktfmt)
  alias(libs.plugins.versionCatalogUpdate)
  alias(libs.plugins.versionUpdate)
}

val jdk = 21

idea {
  module.isDownloadJavadoc = false
  module.isDownloadSources = false
}

allprojects {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }

  tasks.withType<Test> { useJUnitPlatform() }

  afterEvaluate {
    // Fixing kotlin version mismatch https://detekt.dev/docs/gettingstarted/gradle/#dependencies
    configurations
        .matching { it.name == "detekt" }
        .all {
          resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
              useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
            }
          }
        }
  }
}

subprojects {
  apply(plugin = "io.gitlab.arturbosch.detekt")
  apply(plugin = "com.ncorti.ktfmt.gradle")
  apply(plugin = "org.jetbrains.kotlin.jvm")

  java { toolchain { languageVersion.set(JavaLanguageVersion.of(jdk)) } }

  kotlin {
    jvmToolchain(jdk)
    compilerOptions {
      @Suppress("SpellCheckingInspection") freeCompilerArgs.add("-Xjsr305=strict")
      jvmTarget.set(JvmTarget.valueOf("JVM_${jdk}"))
    }
  }

  detekt {
    ignoreFailures = false
    buildUponDefaultConfig = true
    config.setFrom("$rootDir/config/detekt.yml")
    parallel = true
  }

  ktfmt { kotlinLangStyle() }
}
