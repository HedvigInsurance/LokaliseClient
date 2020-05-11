package com.hedvig.lokalise.client.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.hedvig.lokalise.client.model.Key

@JsonIgnoreProperties(ignoreUnknown = true)
data class LokaliseResponse(
    @JsonProperty("keys")
    val keys: List<Key>,
    @JsonProperty("project_id")
    val projectId: String
)