package com.hedvig.lokalise.client.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Translation(
    @JsonProperty("key_id")
    val keyId: Int,
    @JsonProperty("language_iso")
    val languageIso: String,
    @JsonProperty("translation")
    val translation: String,
    @JsonProperty("translation_id")
    val translationId: Int
)