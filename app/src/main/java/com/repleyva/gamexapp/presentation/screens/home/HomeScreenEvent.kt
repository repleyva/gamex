package com.repleyva.gamexapp.presentation.screens.home

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.presentation.base.Event

sealed interface HomeScreenEvent : Event {

    data class Init(val navigator: DestinationsNavigator) : HomeScreenEvent

    data class NavigateToDetailScreen(val game: Game) : HomeScreenEvent

}