package com.repleyva.gamexapp.presentation.screens.bookmark

import com.repleyva.gamexapp.presentation.base.Event

sealed interface BookmarkScreenEvent : Event {

    data object Init : BookmarkScreenEvent
}