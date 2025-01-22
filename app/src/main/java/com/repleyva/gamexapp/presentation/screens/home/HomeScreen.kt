package com.repleyva.gamexapp.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.repleyva.gamexapp.presentation.components.Gap
import com.repleyva.gamexapp.presentation.components.LaunchEffectOnce
import com.repleyva.gamexapp.presentation.screens.home.HomeScreenEvent.*
import com.repleyva.gamexapp.presentation.screens.home.components.GameItem
import com.repleyva.gamexapp.presentation.screens.home.components.GameItemHorizontal
import com.repleyva.gamexapp.presentation.screens.home.components.GameItemHorizontalLoading
import com.repleyva.gamexapp.presentation.screens.home.components.GameItemLoading
import com.repleyva.gamexapp.presentation.screens.home.components.SectionTitle
import com.repleyva.gamexapp.presentation.screens.home.components.TopBar

@Destination(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HomeBody(
            state = state,
            viewModel = viewModel
        )
    }

    LaunchEffectOnce {
        viewModel.eventHandler(Init(navigator))
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun HomeBody(
    state: HomeScreenState,
    viewModel: HomeViewModel,
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        stickyHeader("header") {
            TopBar()
        }

        item {
            SectionTitle(title = "Hot Games")
        }

        item {
            ItemsHorizontal(
                state = state,
                viewModel = viewModel
            )
        }

        item {
            Gap(vertical = 8.dp)
            SectionTitle(title = "Popular Games")
        }

        items(items = state.games, key = { it.id }) {
            GameItem(
                game = it,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            ) { game -> viewModel.eventHandler(NavigateToDetailScreen(game)) }
        }

        if (state.isLoading) {
            items(5) { GameItemLoading(Modifier.padding(horizontal = 24.dp)) }
        }
    }
}

@Composable
private fun ItemsHorizontal(
    state: HomeScreenState,
    viewModel: HomeViewModel,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        items(items = state.hotGames, key = { it.id }) {
            GameItemHorizontal(
                game = it,
                onEvent = viewModel::eventHandler
            )
        }

        if (state.isLoadingHotGames) {
            items(5) {
                GameItemHorizontalLoading()
            }
        }
    }
}