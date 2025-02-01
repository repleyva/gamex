package com.repleyva.gamexapp.presentation.screens.home

import com.repleyva.gamexapp.presentation.base.Event

sealed interface HomeScreenEvent : Event {

    data object Init : HomeScreenEvent

}