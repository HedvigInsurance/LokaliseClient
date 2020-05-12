package com.hedvig.resolver

import org.slf4j.LoggerFactory
import java.util.*

object LocaleResolver {

    fun resolveLocale(acceptLanguage: String?): Locale {
        if (acceptLanguage.isNullOrBlank()) {
            return DEFAULT_LOCALE
        }

        return try {
            val list = Locale.LanguageRange.parse(acceptLanguage)
            Locale.lookup(list, LOCALES) ?: DEFAULT_LOCALE
        } catch (e: IllegalArgumentException) {
            loggger.error("IllegalArgumentException when parsing acceptLanguage: '$acceptLanguage' message: ${e.message}")
            DEFAULT_LOCALE
        }
    }

    private val LOCALES = listOf(
        Locale("en", "se"),
        Locale("sv", "se"),
        Locale("nb", "no"),
        Locale("en", "no")
    )

    private val DEFAULT_LOCALE = Locale("sv", "se")
    private val loggger = LoggerFactory.getLogger(this::class.java)
}
