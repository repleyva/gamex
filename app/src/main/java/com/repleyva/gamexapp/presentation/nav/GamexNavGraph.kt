package com.repleyva.gamexapp.presentation.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.presentation.nav.GamexRouter.BookmarksScreen
import com.repleyva.gamexapp.presentation.nav.GamexRouter.DetailScreen
import com.repleyva.gamexapp.presentation.nav.GamexRouter.HomeScreen
import com.repleyva.gamexapp.presentation.nav.GamexRouter.SearchScreen
import com.repleyva.gamexapp.presentation.nav.GamexRouter.VideoPlayerScreen
import com.repleyva.gamexapp.presentation.screens.bookmark.BookmarkScreen
import com.repleyva.gamexapp.presentation.screens.bookmark.BookmarkViewModel
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreen
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
            val detailViewModel = hiltViewModel<DetailViewModel>()
            navController.previousBackStackEntry?.savedStateHandle?.get<Game>("game")?.let {
                DetailScreen(
                    game = it,
                    viewModel = detailViewModel,
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
            val bookMarkViewModel = hiltViewModel<BookmarkViewModel>()
            BookmarkScreen(bookMarkViewModel) {
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