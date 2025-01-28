package com.repleyva.gamexapp.presentation.screens.detail

import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.presentation.base.State

data class DetailScreenState(
    val game: Game? = null,
    val trailerUrl: String? = null,
    val isLoading: Boolean = false,
    val shareSheetGame: Game? = null,
    val error: String? = null,
) : State