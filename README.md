# GameX
GameX is your ultimate gaming companion! 📱 Explore a vast list of games, view detailed information about each title, and save your favorites to a personalized list. Stay organized and never miss out on the games you love. Perfect for every gaming enthusiast! 🎮✨

## ✨ Features

- 🏠 **Home**
  - 🎮 Browse a list of popular games (*Hot Games*).
  - 🆕 Explore the latest games fetched from the [RAWG API](https://rawg.io/).

- 🔍 **Search**
  - 🔎 Find any game available in the API with the built-in search feature.

- 📌 **Bookmarks**
  - ⭐ Save your favorite games to a personalized list for quick access.

- 🕹️ **Game Details**
  - 📄 View detailed information about each game.
  - ⭐ **Rating**: Check the game's rating.
  - 🎥 **Trailer**: If a trailer is available, you can watch it directly in the app.

# Preview
<img src="_src/preview/home.gif" width="32%"> <img src="_src/preview/detail.gif" width="32%"> <img src="_src/preview/search.gif" width="32%">

# Screenshots
<img src="_src/screenshots/home.png" width="32%"> <img src="_src/screenshots/search.png" width="32%"> <img src="_src/screenshots/detail.png" width="32%">

# Architecture
- Presentation: Responsible for the UI and input management
- Domain: Contains the business logic, including the use cases and repository interfaces
- Data: Responsible for database operations, network requests and caching.

<img name="Architecture" width="100%" src="./_src/clean_architecture.png"/>

# Technologies Used
|                                                                                                                    |                                                                                            |                                                                                     |
|--------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------- |-------------------------------------------------------------------------------------|
| <img src="https://user-images.githubusercontent.com/25181517/185062810-7ee0c3d2-17f2-4a98-9d8a-a9576947692b.png" height="24"> | [**Kotlin**](https://kotlinlang.org/)                                           | Official language for Android development, known for its concise syntax             |
| <img src="https://developer.android.com/static/images/spot-icons/jetpack-compose.svg" height="24">                 | [**Jetpack Compose**](https://developer.android.com/jetpack/compose)                       | A modern toolkit for building native Android UIs                                               |                                        |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**ViewModel**](https://developer.android.com/topic/libraries/architecture/viewmodel)      | Retains UI data across configuration changes                                        |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**Navigation Components**](https://developer.android.com/guide/navigation/navigation-getting-started) | Simplifies app navigation                                               |
| <img src="https://square.github.io/retrofit/static/icon-square.png" height="24">                                   | [**Retrofit**](https://square.github.io/retrofit/)                                         | A type-safe HTTP client for making API requests                                     |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**Room**](https://developer.android.com/training/data-storage/room)                       | A persistence library for local database management        coroutines                       |                                                |
| <img src="https://github.com/user-attachments/assets/22289e1a-d828-4bcf-a7b0-bb8ef8ef9458" height="24">                                               | [**Rawg**](https://rawg.io/)                                                        | The biggest video game database                                                             |
| <img src="https://www.iconpacks.net/icons/2/free-injection-icon-3675-thumb.png" height="24">                       | [**Hilt**](https://dagger.dev/hilt/)                                                       | A dependency injection library that simplifies injecting dependencies in Android apps |
| <img src="https://square.github.io/okhttp/assets/images/icon-square.png" height="24">                              | [**OkHttp**](https://square.github.io/okhttp/)                                             | A networking library for HTTP requests                                              |
| <img src="https://junit.org/junit5/assets/img/junit5-logo.png" height="24">                                        | [**JUnit**](https://junit.org/junit5/)                                                     | A testing framework for writing unit tests                                          |
| <img src="https://avatars.githubusercontent.com/u/34787540?s=280&v=4" height="24">                                 | [**MockK**](https://mockk.io/)                                                             | A mocking framework for unit testing Kotlin code                                    |
| <img src="https://avatars.githubusercontent.com/u/49219790?s=48&v=4" height="24">                                  | [**Turbine**](https://github.com/cashapp/turbine)                                          | A testing library for Kotlin Flows                                                  |

# Requirements

- Android 8.0 (API level 26) or higher  
- Stable internet connection  

# Build setup
App is using the following keys to work:

Rawg IO
- Since both of these keys refer to `buildConfig`, create your own API keys and add them in your `local.properties`:
```
API_KEY = <YOUR_RAWG_API_KEY>
```
