package com.hedvig.lokalise.client

import com.hedvig.lokalise.repository.LokaliseRepository

@Deprecated(
    "Use `LokaliseRepository` instead",
    replaceWith = ReplaceWith("LokaliseRepository", "com.hedvig.lokalise.repository.LokaliseRepository")
)
class LokaliseClient(
    projectId: String,
    apiToken: String,
    pageLimit: Int = 100,
    client: com.hedvig.lokalise.internal.LokaliseClient = com.hedvig.lokalise.internal.LokaliseClient(
        projectId,
        apiToken,
        pageLimit
    )
) : LokaliseRepository(
    projectId,
    apiToken,
    pageLimit,
    client
)