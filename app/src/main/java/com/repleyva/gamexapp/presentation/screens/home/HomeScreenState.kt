package com.repleyva.gamexapp.presentation.screens.home

import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.presentation.base.State

data class HomeScreenState(
    val games: List<Game> = emptyList(),
    val hotGames: List<Game> = emptyList(),
    val isLoadingHotGames: Boolean = true,
    val isLoading: Boolean = true,
    val error: String? = null,
) : State