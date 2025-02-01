package com.repleyva.gamexapp.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.core.domain.vo.Resource.Error
import com.repleyva.core.domain.vo.Resource.Loading
import com.repleyva.core.domain.vo.Resource.Success
import com.repleyva.gamexapp.presentation.base.SimpleMVIBaseViewModel
import com.repleyva.gamexapp.presentation.screens.home.HomeScreenEvent.Init
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
) : SimpleMVIBaseViewModel<HomeScreenState, HomeScreenEvent>() {

    override fun initState(): HomeScreenState = HomeScreenState()

    override fun eventHandler(event: HomeScreenEvent) {
        when (event) {
            is Init -> init()
        }
    }

    private fun init() {
        getAllGames()
        getHotGames()
    }

    private fun getAllGames() {
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

    private fun getHotGames() {
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