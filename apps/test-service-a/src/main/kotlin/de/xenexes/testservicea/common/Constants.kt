package de.xenexes.testservicea.common

import java.time.ZoneId

const val DEFAULT_TIMEZONE_ID_STRING = "UTC"

val DEFAULT_TIMEZONE_ID: ZoneId = ZoneId.of(DEFAULT_TIMEZONE_ID_STRING)

const val DEFAULT_ROLE_USER = "USER"

const val DEFAULT_ROLE_ADMIN = "ADMIN"
