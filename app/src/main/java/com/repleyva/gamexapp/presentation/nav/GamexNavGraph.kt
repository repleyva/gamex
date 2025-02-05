package com.repleyva.gamexapp.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.presentation.components.LaunchEffectOnce
import com.repleyva.gamexapp.presentation.nav.GamexRouter.BookmarksScreen
import com.repleyva.gamexapp.presentation.nav.GamexRouter.DetailScreen
import com.repleyva.gamexapp.presentation.nav.GamexRouter.HomeScreen
import com.repleyva.gamexapp.presentation.nav.GamexRouter.SearchScreen
import com.repleyva.gamexapp.presentation.nav.GamexRouter.VideoPlayerScreen
import com.repleyva.gamexapp.presentation.screens.bookmark.BookmarkScreen
import com.repleyva.gamexapp.presentation.screens.bookmark.BookmarkScreenEvent.Init
import com.repleyva.gamexapp.presentation.screens.bookmark.BookmarkViewModel
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreen
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent
import com.repleyva.gamexapp.presentation.screens.detail.DetailViewModel
import com.repleyva.gamexapp.presentation.screens.home.HomeScreen
import com.repleyva.gamexapp.presentation.screens.home.HomeViewModel
import com.repleyva.gamexapp.presentation.screens.search.SearchScreen
import com.repleyva.gamexapp.presentation.screens.search.SearchViewModel
import com.repleyva.gamexapp.presentation.screens.video_player.VideoPlayer

@Composable
fun GamexNavGraph(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = HomeScreen,
    ) {

        composable<HomeScreen> {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(homeViewModel) {
                navigateToDetails(
                    navController = navController,
                    game = it
                )
            }
        }

        composable<DetailScreen> {

            navController.previousBackStackEntry?.savedStateHandle?.get<Game>("game")?.let {

                val viewModel = hiltViewModel<DetailViewModel>()
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                LaunchEffectOnce {
                    viewModel.eventHandler(DetailScreenEvent.Init(it))
                }

                DetailScreen(
                    state = state,
                    game = it,
                    eventHandler = viewModel::eventHandler,
                    onNavigateBack = {
                        navController.popBackStack()
                        navController.currentBackStackEntry?.savedStateHandle?.remove<Game>("game")
                    },
                    onPlayTrailer = { url ->
                        navigateToVideoPlayer(
                            navController = navController,
                            url = url
                        )
                    }
                )
            }
        }

        composable<SearchScreen> {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(searchViewModel) {
                navigateToDetails(
                    navController = navController,
                    game = it
                )
            }
        }

        composable<BookmarksScreen> {

            val viewModel = hiltViewModel<BookmarkViewModel>()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchEffectOnce {
                viewModel.eventHandler(Init)
            }

            BookmarkScreen(state) {
                navigateToDetails(
                    navController = navController,
                    game = it
                )
            }
        }

        composable<VideoPlayerScreen> {
            navController.previousBackStackEntry?.savedStateHandle?.get<String>("url")?.let {
                VideoPlayer(url = it)
            }
        }

    }
}

private fun navigateToDetails(
    navController: NavController,
    game: Game,
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("game", game)
    navController.navigate(DetailScreen)
}

private fun navigateToVideoPlayer(
    navController: NavController,
    url: String,
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("url", url)
    navController.navigate(VideoPlayerScreen)
}