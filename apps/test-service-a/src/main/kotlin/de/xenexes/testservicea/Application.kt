package de.xenexes.testservicea

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession

@SpringBootApplication @ConfigurationPropertiesScan @EnableJdbcHttpSession class Application

fun main(args: Array<String>) {
    @Suppress("SpreadOperator") runApplication<Application>(*args)
}
