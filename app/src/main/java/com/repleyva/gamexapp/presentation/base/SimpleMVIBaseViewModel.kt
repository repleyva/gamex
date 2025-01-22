package com.repleyva.gamexapp.presentation.base

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class SimpleMVIBaseViewModel<S : State, E : Event> : ViewModel() {

    private val _uiState: MutableStateFlow<S> by lazy { MutableStateFlow(initState()) }
    val uiState get() = _uiState.asStateFlow()

    abstract fun initState(): S

    abstract fun eventHandler(event: E)

    protected fun updateUi(
        handler: S.() -> S,
    ) {
        _uiState.update(handler)
    }

}

interface Event

interface Action

@Stable
interface State