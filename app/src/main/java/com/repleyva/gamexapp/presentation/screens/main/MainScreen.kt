package com.repleyva.gamexapp.presentation.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.repleyva.gamexapp.presentation.nav.BottomBarDestination
import com.repleyva.gamexapp.presentation.nav.BottomNavigationBar
import com.repleyva.gamexapp.presentation.nav.GamexNavGraph
import com.repleyva.gamexapp.presentation.nav.GamexRouter.BookmarksScreen
import com.repleyva.gamexapp.presentation.nav.GamexRouter.HomeScreen
import com.repleyva.gamexapp.presentation.nav.GamexRouter.SearchScreen
import com.repleyva.gamexapp.presentation.ui.theme.GamexAppTheme

@Composable
fun MainScreen() {
    GamexAppTheme {

        val routerToIndex = remember {
            mapOf(
                HomeScreen::class to 0,
                SearchScreen::class to 1,
                BookmarksScreen::class to 2,
            )
        }

        val navController = rememberNavController()

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentDestination = navBackStackEntry?.destination

        val shouldShowBottomBar = remember(key1 = currentDestination) {
            currentDestination?.hierarchy
                ?.any { destination ->
                    routerToIndex.keys.any { route -> destination.hasRoute(route) }
                } == true
        }

        val selectedItem = remember(key1 = currentDestination) {
            currentDestination?.hierarchy
                ?.firstNotNullOfOrNull { destination ->
                    routerToIndex.entries.firstOrNull { (route, _) -> destination.hasRoute(route) }?.value
                } ?: 0
        }

        Scaffold(
            modifier = Modifier.testTag("navigatorTag"),
            bottomBar = {
                if (shouldShowBottomBar) {
                    BottomNavigationBar(
                        selected = selectedItem,
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
                GamexNavGraph(navController)
            }
        }
    }
}