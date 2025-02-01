package com.repleyva.gamexapp.presentation.nav

import kotlinx.serialization.Serializable

@Serializable
sealed interface GamexRouter {

    @Serializable
    data object HomeScreen : GamexRouter

    @Serializable
    data object DetailScreen : GamexRouter

    @Serializable
    data object SearchScreen : GamexRouter

    @Serializable
    data object BookmarksScreen : GamexRouter

    @Serializable
    data object VideoPlayerScreen : GamexRouter
}