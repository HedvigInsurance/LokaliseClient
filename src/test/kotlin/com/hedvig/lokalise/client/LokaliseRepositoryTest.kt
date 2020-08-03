package com.hedvig.lokalise.client

import com.hedvig.lokalise.repository.LokaliseRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import pl.miensol.shouldko.shouldEqual
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
                    ) to "Som tack får både du och dina vänner [%1\$i:REFERRAL_VALUE] kr lägre månadskostnad. Fortsätt bjuda in vänner för att sänka ditt pris ännu mer!",
                    Locale.forLanguageTag(
                        "nb-NO"
                    ) to "Som takk får både du og vennene dine [%1\$i:REFERRAL_VALUE] kr lavere månedskostnad. Fortsett å invitere venner for å senke prisen din enda mer!"
                )
            ), 1
        )

        val repo =
            LokaliseRepository("", "", client = client)

        repo
            .getTranslation("TEST_KEY", Locale.forLanguageTag("sv-SE"), mapOf("REFERRAL_VALUE" to "10"))
            .shouldEqual("Som tack får både du och dina vänner 10 kr lägre månadskostnad. Fortsätt bjuda in vänner för att sänka ditt pris ännu mer!")
    }

    @Test
    fun `should handle placeholders correctly with norwegian locale`() {
        val client = mockk<LokaliseClient>()
        every { client.fetchKeys(any()) } returns Pair(
            mapOf(
                "TEST_KEY" to mapOf(
                    Locale.forLanguageTag(
                        "sv-SE"
                    ) to "Som tack får både du och dina vänner [%1\$i:REFERRAL_VALUE] kr lägre månadskostnad. Fortsätt bjuda in vänner för att sänka ditt pris ännu mer!",
                    Locale.forLanguageTag(
                        "nb-NO"
                    ) to "Som takk får både du og vennene dine [%1\$i:REFERRAL_VALUE] kr lavere månedskostnad. Fortsett å invitere venner for å senke prisen din enda mer!"
                )
            ), 1
        )

        val repo =
            LokaliseRepository("", "", client = client)

        repo
            .getTranslation("TEST_KEY", Locale.forLanguageTag("nb-NO"), mapOf("REFERRAL_VALUE" to "10"))
            .shouldEqual("Som takk får både du og vennene dine 10 kr lavere månedskostnad. Fortsett å invitere venner for å senke prisen din enda mer!")
    }
}