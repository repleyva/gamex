package com.repleyva.gamexapp.presentation.screens.bookmark

import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.presentation.base.State

data class BookmarkScreenState(
    val games: List<Game> = emptyList(),
) : State