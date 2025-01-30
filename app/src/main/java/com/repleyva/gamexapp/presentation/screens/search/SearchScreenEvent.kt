package com.repleyva.gamexapp.presentation.screens.search

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.presentation.base.Event

sealed interface SearchScreenEvent : Event {

    data class Init(val navigator: DestinationsNavigator) : SearchScreenEvent

    data class NavigateToDetailScreen(val game: Game) : SearchScreenEvent

    data class OnSearchQueryChange(
        val query: String,
    ) : SearchScreenEvent
}