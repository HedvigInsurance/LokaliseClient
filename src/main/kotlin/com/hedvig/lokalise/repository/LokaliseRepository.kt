package com.hedvig.lokalise.repository

import com.hedvig.lokalise.client.LokaliseClient
import com.hedvig.util.logger
import java.util.Locale

/**
 * Provides access to translations from Lokalise.
 */
class LokaliseRepository(
    private val projectId: String,
    private val apiToken: String,
    private val pageLimit: Int = 100,
    private val client: LokaliseClient = LokaliseClient(
        projectId,
        apiToken,
        pageLimit
    )
) {
    private val logger by logger()
    private val keyToTranslationsMap: Map<String, Map<Locale, String>>

    init {
        keyToTranslationsMap = fetchPaginated(1, mutableMapOf())
    }

    private fun fetchPaginated(
        page: Int,
        mutableMap: MutableMap<String, Map<Locale, String>>
    ): Map<String, Map<Locale, String>> {
        val (response, totalNumberOfPages) = client.fetchKeys(page)
        mutableMap.putAll(response)

        val nextPage = page.plus(1)

        return if (nextPage > totalNumberOfPages) {
            mutableMap.toMap()
        } else {
            fetchPaginated(nextPage, mutableMap)
        }
    }

    fun getTranslation(key: String, locale: Locale, replacements: Map<String, String> = emptyMap()): String? {
        val translation = keyToTranslationsMap[key]?.let { it[locale] } ?: return null

        return lokalisePlaceholderSyntaxMatcher
            .findAll(translation)
            .fold(translation) { acc, curr ->
                val replacementName = curr.groups[1]?.value
                if (replacementName == null) {
                    logger.warn("Unable to find replacement name in key: $key with translation: $translation")
                    return acc
                }
                val replacement = replacements[replacementName]
                if (replacement == null) {
                    logger.warn("Requested replacement: $replacementName not found in key: $key with translation: $translation")
                    return acc
                }
                return@fold lokalisePlaceholderSyntaxMatcher.replaceFirst(acc, replacement)
            }
    }

    companion object {
        private val lokalisePlaceholderSyntaxMatcher = Regex("\\[\\%\\d+\\\$[sif]\\:(\\w+)\\]")
    }
}