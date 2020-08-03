package com.hedvig.lokalise.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.hedvig.lokalise.client.model.LokaliseResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Locale

class LokaliseClient(
    private val projectId: String,
    private val apiToken: String,
    private val pageLimit: Int
) {
    private val client = OkHttpClient()
    private val objectMapper = ObjectMapper()
    private val requestBuilder = Request.Builder()

    fun fetchKeys(page: Int): Pair<Map<String, Map<Locale, String>>, Int> {
        val request: Request = requestBuilder
            .url(
                createLokaliseRequestUrl(
                    projectId,
                    pageLimit,
                    page
                )
            )
            .addHeader(API_TOKEN_HEADER, apiToken)
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        return Pair(
            parseLokaliseResponse(
                objectMapper.readValue(
                    responseBody,
                    LokaliseResponse::class.java
                )
            ), response.header(PAGE_COUNT_HEADER)!!.toInt()
        )
    }

    private fun parseLokaliseResponse(lokaliseResponse: LokaliseResponse) = lokaliseResponse.keys.map { key ->
        key.keyName.other to key.translations.map { translation ->
            if (translation.languageIso.matches(languageIsoRegex)) {
                val split = translation.languageIso.split(split)
                Locale(split[0], split[1]) to translation.translation
            } else {
                Locale(translation.languageIso) to translation.translation
            }
        }.toMap()
    }.toMap()

    companion object {
        private val languageIsoRegex = Regex("[a-zA-Z][a-zA-Z]_[a-zA-Z][a-zA-Z]")
        private val split = Regex("_")
        private const val PAGE_COUNT_HEADER = "X-Pagination-Page-Count"
        private const val API_TOKEN_HEADER = "X-Api-Token"

        private fun createLokaliseRequestUrl(projectId: String, limit: Int, page: Int): String =
            "https://api.lokalise.com/api2/projects/${projectId}/keys?include_translations=1&limit=$limit&page=$page"
    }
}