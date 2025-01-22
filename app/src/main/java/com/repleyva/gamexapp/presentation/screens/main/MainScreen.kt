package com.repleyva.gamexapp.presentation.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.repleyva.gamexapp.presentation.nav.BottomBarDestination
import com.repleyva.gamexapp.presentation.nav.BottomNavigationBar
import com.repleyva.gamexapp.presentation.screens.NavGraphs
import com.repleyva.gamexapp.presentation.ui.theme.GamexAppTheme

@Composable
fun MainScreen() {
    GamexAppTheme {

        val navController = rememberNavController()
        val shouldShowBottomBar = navController
            .currentBackStackEntryAsState().value?.destination?.route in BottomBarDestination.entries
            .map { it.direction.route }

        Scaffold(
            bottomBar = {
                if (shouldShowBottomBar) {
                    BottomNavigationBar(
                        navController = navController,
                        items = BottomBarDestination.entries,
                        onItemClick = {
                            navController.navigate(it.direction) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            },
        ) {
            Box(modifier = Modifier.padding(it)) {
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    navController = navController
                )
            }
        }
    }
}