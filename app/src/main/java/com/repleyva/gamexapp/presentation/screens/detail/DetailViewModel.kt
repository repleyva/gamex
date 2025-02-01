package com.repleyva.gamexapp.presentation.screens.detail

import androidx.lifecycle.viewModelScope
import com.repleyva.core.domain.model.Game
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.core.domain.vo.Resource.Error
import com.repleyva.core.domain.vo.Resource.Loading
import com.repleyva.core.domain.vo.Resource.Success
import com.repleyva.gamexapp.presentation.base.SimpleMVIBaseViewModel
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.BookmarkGame
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.Init
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.ShareGame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
) : SimpleMVIBaseViewModel<DetailScreenState, DetailScreenEvent>() {

    override fun initState(): DetailScreenState = DetailScreenState()

    override fun eventHandler(event: DetailScreenEvent) {
        when (event) {
            is Init -> init(event.game)
            is BookmarkGame -> setBookmarked(event.id, event.bookmarked)
            is ShareGame -> shareGame(event.game, event.dismissed)
        }
    }

    private fun init(
        game: Game,
    ) {
        updateUi { copy(game = game) }
        viewModelScope.launch {
            fetchDetailGame(game.id)
            fetchGameTrailer(game.id)
        }
    }

    private fun fetchDetailGame(gameId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            gameUseCase.getGameDetails(gameId).onEach { result ->
                updateUi {
                    when (result) {
                        is Error -> copy(isLoading = false, error = result.message)
                        is Loading -> copy(isLoading = true)
                        is Success -> copy(game = result.data, isLoading = false, error = null)

                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun fetchGameTrailer(gameId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            gameUseCase.fetchGameTrailer(gameId).onEach { result ->
                updateUi {
                    when (result) {
                        is Error -> copy(isLoading = false, error = result.message)
                        is Loading -> copy(isLoading = true)
                        is Success -> this
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun shareGame(
        game: Game?,
        isDismissed: Boolean,
    ) {
        updateUi { copy(shareSheetGame = if (isDismissed) null else game) }
    }

    private fun setBookmarked(
        id: Long,
        isBookmarked: Boolean,
    ) {
        viewModelScope.launch {
            gameUseCase.setIsFavorites(isBookmarked, id)
        }
    }

}