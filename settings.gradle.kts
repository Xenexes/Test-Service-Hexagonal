plugins { id("com.gradle.develocity") version ("3.19") }

develocity {
  buildScan {
    termsOfUseUrl = "https://gradle.com/help/legal-terms-of-use"
    termsOfUseAgree = "yes"
  }
}

rootProject.name = "Test-Service-Hexagonal"

include(":apps:test-service-a")
