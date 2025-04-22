package de.xenexes.testservicea

import com.ninjasquad.springmockk.MockkBean
import de.xenexes.testservicea.common.PostgresTestContainerInitializer
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@Suppress("MaxLineLength")
@ExtendWith(SpringExtension::class)
@SpringBootTest
@ContextConfiguration(initializers = [PostgresTestContainerInitializer::class])
@Profile("test")
internal class ApplicationTest {

    @Suppress("UnusedPrivateProperty")
    @MockkBean
    private lateinit var mailSenderValidatorAutoConfiguration: MailSenderValidatorAutoConfiguration

    @Test
    @DisplayName("Check if context loads")
    fun contextLoads() {
        // check if context is loaded
    }
}
