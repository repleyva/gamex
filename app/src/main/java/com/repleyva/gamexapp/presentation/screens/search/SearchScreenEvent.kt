package com.repleyva.gamexapp.presentation.screens.search

import com.repleyva.gamexapp.presentation.base.Event

sealed interface SearchScreenEvent : Event {

    data class OnSearchQueryChange(
        val query: String,
    ) : SearchScreenEvent
}