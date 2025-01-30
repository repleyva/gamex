package com.repleyva.gamexapp.presentation.screens.search

import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.repleyva.core.domain.model.Game
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.core.domain.vo.Resource
import com.repleyva.gamexapp.presentation.base.SimpleMVIBaseViewModel
import com.repleyva.gamexapp.presentation.screens.destinations.DetailScreenDestination
import com.repleyva.gamexapp.presentation.screens.search.SearchScreenEvent.Init
import com.repleyva.gamexapp.presentation.screens.search.SearchScreenEvent.NavigateToDetailScreen
import com.repleyva.gamexapp.presentation.screens.search.SearchScreenEvent.OnSearchQueryChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
) : SimpleMVIBaseViewModel<SearchScreenState, SearchScreenEvent>() {

    private var navigator: DestinationsNavigator? = null

    private var searchJob: Job? = null

    override fun initState(): SearchScreenState = SearchScreenState()

    override fun eventHandler(event: SearchScreenEvent) {
        when (event) {
            is Init -> init(event.navigator)
            is NavigateToDetailScreen -> navigateToDetailScreen(event.game)
            is OnSearchQueryChange -> onSearchQueryChanged(event.query)
        }
    }

    private fun init(navigator: DestinationsNavigator) {
        this.navigator = navigator
    }

    private fun onSearchQueryChanged(query: String) {
        updateUi { copy(query = query) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            if (query.isNotEmpty()) {
                searchGames(query)
            } else {
                updateUi { copy(games = emptyList(), error = null, isLoading = false) }
            }
        }
    }

    private fun searchGames(query: String) {
        gameUseCase.searchGames(query).onEach { result ->
            updateUi {
                when (result) {
                    is Resource.Error -> copy(error = result.message, isLoading = false)
                    is Resource.Loading -> copy(isLoading = true)
                    is Resource.Success -> copy(games = result.data, error = null, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun navigateToDetailScreen(game: Game) {
        navigator?.navigate(DetailScreenDestination(game))
    }

}