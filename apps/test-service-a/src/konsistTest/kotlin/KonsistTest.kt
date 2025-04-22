package de.xenexes.testservicea

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.jupiter.api.Test

class KonsistTest {
    @Test
    fun `hexagonal architecture layers dependencies`() {
        Konsist.scopeFromProject().assertArchitecture {
            val domain =
                Layer(name = "Domain", rootPackage = "de.xenexes.testservicea.account.domain..")
            val inbound =
                Layer(
                    name = "InboundAdapters",
                    rootPackage = "de.xenexes.testservicea.account.adapter.inbound..",
                )
            val outbound =
                Layer(
                    name = "OutboundAdapters",
                    rootPackage = "de.xenexes.testservicea.account.adapter.outbound..",
                )
            val application =
                Layer(
                    name = "Application",
                    rootPackage = "de.xenexes.testservicea.account.application..",
                )

            domain.dependsOnNothing()
            inbound.dependsOn(domain)
            outbound.dependsOn(domain)
            application.dependsOn(domain)
        }
    }
}
