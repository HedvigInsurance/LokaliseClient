# LokaliseClient

### Usage

##### Maven
```
<dependency>
	<groupId>com.hedvig</groupId>
	<artifactId>lokalise-client</artifactId>
	<version>[latest version]</version>
</dependency>
```

##### Gradle
###### Groovy
```implementation 'com.hedvig:lokalise-client:[latest version]'```
###### Kotlin dsl
```implementation('com.hedvig:lokalise-client:[latest version]')```

#### Spring(sugggestion)
Create `LocalizationService` component:

```
@Component
class LocalizationService(
    @Value("\${lokalise.projectId}")
    private val projectId: String,
    @Value("\${lokalise.apiToken}")
    private val apiToken: String
) {

    val client = LokaliseRepository(
        projectId,
        apiToken
    )

    fun getTranslation(key: String, locale: Locale) =
        client.getTranslation(key, locale)
}
```

Inject `LocalizationService` and use `LokaliseRepository.getTranslation(key, locale)` where ever you need a translated string.

##### Resolve `Locale`
Use `LocaleResolver.resolveLocale(acceptLanguageString)` to resolve a Locale from a [Accept-Language](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Language) header

### TODO's
* Enable reload with webhooks

#### Development
Upload to bintray, run: `./gradlew clean build bintrayUpload -PbintrayUser="[bintray-username]" -PbintrayKey="[bintray-api-token]"`
