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
Upload to GitHub Packages:
1. Update `versionName` in `build.gradle.kts` to an appropriate version
2. run: `./gradlew clean publish`

Note:
The GitHub token have to be in place to be able to deploy the lib. To achieve this in a local development environment:

1. Create a personal GitHub development token with `write:packages` access.
2. Create a new file `gradle.properties` in the project root (or globally in `~/.gradle` folder) and insert
   the token generated in step (1) like this:

GITHUB_USER=<github user>
GITHUB_TOKEN=<github token>
