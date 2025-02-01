package com.repleyva.core.domain.constants

import com.repleyva.core.domain.model.Game

object Constants {

    val dummyGame = Game(
        id = 1000L,
        slug = "slug",
        name = "name",
        released = "released",
        tba = false,
        backgroundImage = "backgroundImage",
        rating = 0.0,
        ratingTop = 0,
        ratingsCount = 0,
        reviewsTextCount = 0,
        added = 0,
        metacritic = 0,
        playtime = 0,
        suggestionsCount = 0,
        updated = "updated",
        reviewsCount = 0,
        saturatedColor = "saturatedColor",
        dominantColor = "dominantColor",
        parentPlatforms = listOf(),
        genres = listOf(),
        stores = listOf(),
        tags = listOf(),
        esrbRating = "esrbRating",
        shortScreenshots = listOf(),
        isFavorites = false,
        description = "description",
        trailerUrl = "trailerUrl",
    )
}