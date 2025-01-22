package com.repleyva.gamexapp.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.repleyva.core.domain.model.Game
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.core.domain.vo.Resource.Error
import com.repleyva.core.domain.vo.Resource.Loading
import com.repleyva.core.domain.vo.Resource.Success
import com.repleyva.gamexapp.presentation.base.SimpleMVIBaseViewModel
import com.repleyva.gamexapp.presentation.screens.destinations.DetailScreenDestination
import com.repleyva.gamexapp.presentation.screens.home.HomeScreenEvent.Init
import com.repleyva.gamexapp.presentation.screens.home.HomeScreenEvent.NavigateToDetailScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
) : SimpleMVIBaseViewModel<HomeScreenState, HomeScreenEvent>() {

    private var navigator: DestinationsNavigator? = null

    override fun initState(): HomeScreenState = HomeScreenState()

    override fun eventHandler(event: HomeScreenEvent) {
        when (event) {
            is Init -> init(event.navigator)
            is NavigateToDetailScreen -> navigateToDetailScreen(event.game)
        }
    }

    private fun init(navigator: DestinationsNavigator) {
        this.navigator = navigator
        viewModelScope.launch {
            getAllGames()
            getHotGames()
        }
    }

    private fun getAllGames() {
        viewModelScope.launch(Dispatchers.IO) {
            gameUseCase.getAllGames().onEach { result ->
                updateUi {
                    when (result) {
                        is Error -> copy(isLoading = false, error = result.message)
                        is Loading -> copy(isLoading = true)
                        is Success -> copy(games = result.data, isLoading = false, error = null)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getHotGames() {
        viewModelScope.launch(Dispatchers.IO) {
            gameUseCase.getHotGames().onEach { result ->
                updateUi {
                    when (result) {
                        is Error -> copy(isLoadingHotGames = false, error = result.message)
                        is Loading -> copy(isLoadingHotGames = true)
                        is Success -> copy(hotGames = result.data, isLoadingHotGames = false, error = null)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun navigateToDetailScreen(game: Game) {
        navigator?.navigate(DetailScreenDestination(game))
    }

}