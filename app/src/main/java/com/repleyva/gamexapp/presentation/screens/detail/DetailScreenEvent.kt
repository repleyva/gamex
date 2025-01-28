package com.repleyva.gamexapp.presentation.screens.detail

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.presentation.base.Event

sealed interface DetailScreenEvent : Event {

    data class Init(
        val game: Game,
        val navigator: DestinationsNavigator,
    ) : DetailScreenEvent

    data object NavigateBack : DetailScreenEvent

    data class BookmarkGame(
        val id: Long,
        val bookmarked: Boolean,
    ) : DetailScreenEvent

    data class ShareGame(
        val game: Game? = null,
        val dismissed: Boolean = false,
    ) : DetailScreenEvent

    data class PlayTrailer(
        val url: String,
    ) : DetailScreenEvent

}