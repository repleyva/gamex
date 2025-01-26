package com.repleyva.gamexapp.presentation.screens.bookmark

import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.repleyva.core.domain.model.Game
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.gamexapp.presentation.base.SimpleMVIBaseViewModel
import com.repleyva.gamexapp.presentation.screens.bookmark.BookmarkScreenEvent.Init
import com.repleyva.gamexapp.presentation.screens.bookmark.BookmarkScreenEvent.NavigateToDetailScreen
import com.repleyva.gamexapp.presentation.screens.destinations.DetailScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
) : SimpleMVIBaseViewModel<BookmarkScreenState, BookmarkScreenEvent>() {

    private var navigator: DestinationsNavigator? = null

    override fun initState(): BookmarkScreenState = BookmarkScreenState()

    override fun eventHandler(event: BookmarkScreenEvent) {
        when (event) {
            is Init -> init(event.navigation)
            is NavigateToDetailScreen -> navigateToDetailScreen(event.game)
        }
    }

    private fun init(navigator: DestinationsNavigator) {
        this.navigator = navigator
        getBookmarkedGames()
    }

    private fun getBookmarkedGames() {
        viewModelScope.launch(Dispatchers.IO) {
            gameUseCase.getBookmarkedGames().onEach { games ->
                updateUi { copy(games = games) }
            }.launchIn(viewModelScope)
        }
    }

    private fun navigateToDetailScreen(game: Game) {
        navigator?.navigate(DetailScreenDestination(game))
    }

}