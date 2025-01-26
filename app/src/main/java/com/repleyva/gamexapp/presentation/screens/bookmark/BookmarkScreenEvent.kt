package com.repleyva.gamexapp.presentation.screens.bookmark

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.presentation.base.Event

sealed interface BookmarkScreenEvent : Event {

    data class Init(val navigation: DestinationsNavigator) : BookmarkScreenEvent

    data class NavigateToDetailScreen(val game: Game) : BookmarkScreenEvent
}