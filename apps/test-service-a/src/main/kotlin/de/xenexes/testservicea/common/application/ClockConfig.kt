package de.xenexes.testservicea.common.application

import de.xenexes.testservicea.common.DEFAULT_TIMEZONE_ID
import java.time.Clock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClockConfig {
    @Bean fun clock(): Clock = Clock.system(DEFAULT_TIMEZONE_ID)
}
