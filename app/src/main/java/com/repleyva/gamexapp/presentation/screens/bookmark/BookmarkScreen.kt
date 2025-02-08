package com.repleyva.gamexapp.presentation.screens.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.R
import com.repleyva.gamexapp.presentation.screens.home.components.GameItem
import com.repleyva.gamexapp.presentation.ui.theme.Neutral50
import com.repleyva.gamexapp.presentation.ui.theme.Primary50

@Composable
fun BookmarkScreen(
    state: BookmarkScreenState,
    onDetailScreen: (game: Game) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .testTag("bookmarkScreen"),
        contentPadding = PaddingValues(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Text(
                text = stringResource(id = R.string.title_bookmark),
                style = MaterialTheme.typography.headlineMedium,
                color = Primary50
            )
            Text(
                text = stringResource(id = R.string.label_bookmark_description),
                style = MaterialTheme.typography.bodyMedium,
                color = Neutral50
            )
        }

        if (state.games.isNotEmpty()) {
            items(items = state.games, key = { it.id }) {
                GameItem(
                    game = it,
                    onEvent = { game ->
                        onDetailScreen(game)
                    }
                )
            }
        }
    }
}