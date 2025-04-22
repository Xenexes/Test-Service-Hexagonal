package de.xenexes.testservicea.account.domain.validation

import de.xenexes.testservicea.account.domain.exceptions.InvalidEmailFormatException
import de.xenexes.testservicea.account.domain.exceptions.InvalidPasswordFormatException
import java.util.regex.Pattern

object AccountValidator {
    @Suppress("MaxLineLength")
    private val EMAIL_REGEX =
        Pattern.compile(
            "^(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])\$"
        )
    private val PASSWORD_REGEX =
        Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$")

    fun validateEmail(email: String) {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw InvalidEmailFormatException("Invalid email format: $email")
        }
    }

    fun validatePassword(password: String) {
        if (!PASSWORD_REGEX.matcher(password).matches()) {
            throw InvalidPasswordFormatException(
                "Password must be at least 8 characters long and include a digit, lowercase letter, " +
                    "uppercase letter, and special character."
            )
        }
    }
}
