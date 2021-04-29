package com.hedvig.lokalise.client

import com.hedvig.lokalise.repository.LokaliseRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Locale

internal class LokaliseRepositoryTest {
    @Test
    fun `should handle placeholders correctly with swedish locale`() {
        val client = mockk<LokaliseClient>()
        every { client.fetchKeys(any()) } returns Pair(
            mapOf(
                "TEST_KEY" to mapOf(
                    Locale.forLanguageTag(
                        "sv-SE"
                    ) to "Swedish Placeholder [%1\$i:REFERRAL_VALUE] kr",
                    Locale.forLanguageTag(
                        "nb-NO"
                    ) to "Norwegian Placeholder [%1\$i:REFERRAL_VALUE] kr",
                    Locale.forLanguageTag(
                        "da-DK"
                    ) to "Danish Placeholder [%1\$i:REFERRAL_VALUE] kr"
                )
            ), 1
        )

        val repo =
            LokaliseRepository("", "", client = client)

        val translation = repo
            .getTranslation("TEST_KEY", Locale.forLanguageTag("sv-SE"), mapOf("REFERRAL_VALUE" to "10"))

        assertEquals(
            translation,
            "Swedish Placeholder 10 kr"
        )
    }

    @Test
    fun `should handle placeholders correctly with norwegian locale`() {
        val client = mockk<LokaliseClient>()
        every { client.fetchKeys(any()) } returns Pair(
            mapOf(
                "TEST_KEY" to mapOf(
                    Locale.forLanguageTag(
                        "sv-SE"
                    ) to "Swedish Placeholder [%1\$i:REFERRAL_VALUE] kr",
                    Locale.forLanguageTag(
                        "nb-NO"
                    ) to "Norwegian Placeholder [%1\$i:REFERRAL_VALUE] kr",
                    Locale.forLanguageTag(
                        "da-DK"
                    ) to "Danish Placeholder [%1\$i:REFERRAL_VALUE] kr"
                )
            ), 1
        )

        val repo =
            LokaliseRepository("", "", client = client)

        val translation = repo
            .getTranslation("TEST_KEY", Locale.forLanguageTag("nb-NO"), mapOf("REFERRAL_VALUE" to "10"))

        assertEquals(
            translation,
            "Norwegian Placeholder 10 kr"
        )
    }

    @Test
    fun `should handle placeholders correctly with danish locale`() {
        val client = mockk<LokaliseClient>()
        every { client.fetchKeys(any()) } returns Pair(
            mapOf(
                "TEST_KEY" to mapOf(
                    Locale.forLanguageTag(
                        "sv-SE"
                    ) to "Swedish Placeholder [%1\$i:REFERRAL_VALUE] kr",
                    Locale.forLanguageTag(
                        "nb-NO"
                    ) to "Norwegian Placeholder [%1\$i:REFERRAL_VALUE] kr",
                    Locale.forLanguageTag(
                        "da-DK"
                    ) to "Danish Placeholder [%1\$i:REFERRAL_VALUE] kr"
                )
            ), 1
        )

        val repo =
            LokaliseRepository("", "", client = client)

        val translation = repo
            .getTranslation("TEST_KEY", Locale.forLanguageTag("da-DK"), mapOf("REFERRAL_VALUE" to "10"))

        assertEquals(
            translation,
            "Danish Placeholder 10 kr"
        )
    }

    @Test
    fun `should handle multiple placeholders correctly`() {
        val client = mockk<LokaliseClient>()
        every { client.fetchKeys(any()) } returns Pair(
            mapOf(
                "TEST_KEY" to mapOf(
                    Locale.forLanguageTag(
                        "nb-NO"
                    ) to "Discount: [%1\$s:discount]-rabatt Minumum: [%2\$s:minimumValue]/mo."
                )
            ), 1
        )

        val repo =
            LokaliseRepository("", "", client = client)

        val translation = repo
            .getTranslation(
                "TEST_KEY",
                Locale.forLanguageTag("nb-NO"),
                mapOf("discount" to "10 kr", "minimumValue" to "0 kr")
            )

        assertEquals(
            translation,
            "Discount: 10 kr-rabatt Minumum: 0 kr/mo."
        )
    }
}