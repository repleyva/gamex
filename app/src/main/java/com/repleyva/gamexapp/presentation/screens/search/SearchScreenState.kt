package com.repleyva.gamexapp.presentation.screens.search

import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.presentation.base.State

data class SearchScreenState(
    val games: List<Game> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
) : State