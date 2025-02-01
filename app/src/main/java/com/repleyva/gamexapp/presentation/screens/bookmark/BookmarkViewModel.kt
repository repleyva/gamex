package com.repleyva.gamexapp.presentation.screens.bookmark

import androidx.lifecycle.viewModelScope
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.gamexapp.presentation.base.SimpleMVIBaseViewModel
import com.repleyva.gamexapp.presentation.screens.bookmark.BookmarkScreenEvent.Init
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
) : SimpleMVIBaseViewModel<BookmarkScreenState, BookmarkScreenEvent>() {

    override fun initState(): BookmarkScreenState = BookmarkScreenState()

    override fun eventHandler(event: BookmarkScreenEvent) {
        when (event) {
            is Init -> init()
        }
    }

    private fun init() {
        getBookmarkedGames()
    }

    private fun getBookmarkedGames() {
        gameUseCase.getBookmarkedGames().onEach { games ->
            updateUi { copy(games = games) }
        }.launchIn(viewModelScope)
    }

}