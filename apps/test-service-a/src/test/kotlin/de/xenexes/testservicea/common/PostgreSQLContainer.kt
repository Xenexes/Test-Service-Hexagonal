package de.xenexes.testservicea.common

import org.testcontainers.containers.PostgreSQLContainer

class PostgreSQLContainer(imageName: String) :
    PostgreSQLContainer<de.xenexes.testservicea.common.PostgreSQLContainer>(imageName) {

    override fun getDriverClassName(): String {
        return "org.postgresql.Driver"
    }
}
