package com.hedvig.lokalise.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.hedvig.lokalise.client.model.LokaliseResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*

class LokaliseClient(
    private val projectId: String,
    private val apiToken: String,
    private val pageLimit: Int = 100
) {

    private val client = OkHttpClient()
    private val objectMapper = ObjectMapper()
    private val requestBuilder = Request.Builder()

    private val keyToTranslationsMap: Map<String, Map<Locale, String>>

    init {
        keyToTranslationsMap = fetchPaginated(1, mutableMapOf())
    }

    private fun fetchPaginated(
        page: Int,
        mutableMap: MutableMap<String, Map<Locale, String>>
    ): Map<String, Map<Locale, String>> {
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

        mutableMap.putAll(parseLokaliseResponse(objectMapper.readValue(responseBody, LokaliseResponse::class.java)))

        val nextPage = page.plus(1)

        return if (nextPage > response.header(PAGE_COUNT_HEADER)!!.toInt()) {
            mutableMap.toMap()
        } else {
            fetchPaginated(nextPage, mutableMap)
        }
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

    fun getTranslation(key: String, locale: Locale) = (
            keyToTranslationsMap[key] ?: error(
                "Failed to find key: $key"
            ))[locale] ?: error(
        "[Key: ${keyToTranslationsMap[key]}] did have locale: $locale"
    )

    companion object {
        private val languageIsoRegex = Regex("[a-zA-Z][a-zA-Z]_[a-zA-Z][a-zA-Z]")
        private val split = Regex("_")

        private const val PAGE_COUNT_HEADER = "X-Pagination-Page-Count"
        private const val API_TOKEN_HEADER = "X-Api-Token"

        private fun createLokaliseRequestUrl(projectId: String, limit: Int, page: Int): String =
            "https://api.lokalise.com/api2/projects/${projectId}/keys?include_translations=1&limit=$limit&page=$page"
    }
}