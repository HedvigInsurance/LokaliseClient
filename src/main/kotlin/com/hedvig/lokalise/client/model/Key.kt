package com.hedvig.lokalise.client.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Key(
    @JsonProperty("key_name")
    val keyName: KeyName,
    @JsonProperty("translations")
    val translations: List<Translation>
)