package de.xenexes.testservicea.common

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

class PostgresTestContainerInitializer :
    ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Container
    var container: PostgreSQLContainer<*> =
        de.xenexes.testservicea.common
            .PostgreSQLContainer("postgres:17-alpine")
            .withDatabaseName("connecgt-with-locals")
            .withUsername("test")
            .withPassword("test")

    @DynamicPropertySource
    private fun applyParameters(applicationContext: ConfigurableApplicationContext) {
        TestPropertyValues.of(
                "spring.datasource.driver-class-name=" + container.driverClassName,
                "datasource.hikari.driverClassName=" + container.driverClassName,
                "spring.datasource.url=" + container.jdbcUrl,
                "spring.datasource.user=" + container.username,
                "spring.datasource.password=" + container.password,
                "spring.datasource.username=" + container.username,
                "spring.spring.datasource.password=" + container.password,
                "spring.flyway.enabled=" + true,
                "spring.flyway.url=" + container.jdbcUrl,
                "spring.flyway.user=" + container.username,
                "spring.flyway.password=" + container.password,
            )
            .applyTo(applicationContext.environment)
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        container.start()
        applyParameters(applicationContext)
    }
}
