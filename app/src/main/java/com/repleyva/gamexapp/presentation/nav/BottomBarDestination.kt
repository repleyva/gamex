package com.repleyva.gamexapp.presentation.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.repleyva.gamexapp.R
import com.repleyva.gamexapp.presentation.nav.GamexRouter.BookmarksScreen
import com.repleyva.gamexapp.presentation.nav.GamexRouter.HomeScreen
import com.repleyva.gamexapp.presentation.nav.GamexRouter.SearchScreen

enum class BottomBarDestination(
    val direction: GamexRouter,
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
) {
    Home(HomeScreen, R.string.title_home, R.drawable.ic_ghost),
    Search(SearchScreen, R.string.title_search, R.drawable.ic_search),
    Bookmark(BookmarksScreen, R.string.title_bookmark, R.drawable.ic_bookmark),
}