package com.hedvig.lokalise.client

import com.hedvig.lokalise.repository.LokaliseRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import pl.miensol.shouldko.shouldEqual
import java.util.Locale

internal class LokaliseClientTest {
    @Test
    fun `should handle placeholders correctly`() {
        val repo = mockk<LokaliseRepository>()
        every { repo.fetchKeys(any()) } returns Pair(
            mapOf(
                "TEST_KEY" to mapOf(
                    Locale.forLanguageTag(
                        "sv_SE"
                    ) to "Som tack får både du och dina vänner [%1\$i:REFERRAL_VALUE] kr lägre månadskostnad. Fortsätt bjuda in vänner för att sänka ditt pris ännu mer!"
                )
            ), 1
        )

        val client = LokaliseClient("", "", lokaliseRepository = repo)

        client
            .getTranslation("TEST_KEY", Locale.forLanguageTag("sv_SE"), mapOf("REFERRAL_VALUE" to "10"))
            .shouldEqual("Som tack får både du och dina vänner 10 kr lägre månadskostnad. Fortsätt bjuda in vänner för att sänka ditt pris ännu mer!")
    }
}