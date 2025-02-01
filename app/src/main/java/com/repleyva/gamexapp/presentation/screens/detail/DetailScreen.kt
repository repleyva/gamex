package com.repleyva.gamexapp.presentation.screens.detail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.R
import com.repleyva.gamexapp.presentation.components.Gap
import com.repleyva.gamexapp.presentation.components.LaunchEffectOnce
import com.repleyva.gamexapp.presentation.extensions.shareLink
import com.repleyva.gamexapp.presentation.extensions.shimmerEffect
import com.repleyva.gamexapp.presentation.extensions.showToast
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.BookmarkGame
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.Init
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.ShareGame
import com.repleyva.gamexapp.presentation.screens.detail.components.GamePoster
import com.repleyva.gamexapp.presentation.screens.detail.components.GeneralGameInfo
import com.repleyva.gamexapp.presentation.screens.detail.components.Screenshots
import com.repleyva.gamexapp.presentation.screens.home.components.TagGroup
import com.repleyva.gamexapp.presentation.ui.theme.Neutral40
import com.repleyva.gamexapp.presentation.ui.theme.Neutral50
import com.repleyva.gamexapp.presentation.ui.theme.Primary70

@Composable
fun DetailScreen(
    game: Game,
    viewModel: DetailViewModel,
    onPlayTrailer: (String) -> Unit,
    onNavigateBack: () -> Unit,
) {

    val context = LocalContext.current

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val savedState = rememberSaveable { mutableStateOf(game.isFavorites) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        state.game?.let { game ->

            DetailBody(
                game = game,
                viewModel = viewModel,
                savedState = savedState,
                context = context,
                onPlayTrailer = onPlayTrailer,
                onNavigateBack = onNavigateBack
            )

            state.shareSheetGame?.let {
                context.shareLink("Check out this ${game.name} game on Gamex!")
                viewModel.eventHandler(ShareGame(dismissed = true))
            }

        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shimmerEffect(),
            )
        }
    }

    LaunchEffectOnce {
        viewModel.eventHandler(Init(game))
    }
}

@Composable
private fun DetailBody(
    game: Game,
    viewModel: DetailViewModel,
    savedState: MutableState<Boolean>,
    context: Context,
    onPlayTrailer: (String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy((-30).dp),
    ) {

        GamePoster(
            game = game,
            onPlayTrailer = onPlayTrailer,
            onNavigateBack = onNavigateBack,
            onShareGame = { viewModel.eventHandler(ShareGame(game)) }
        )

        DetailsContent(
            game = game,
            savedState = savedState,
            viewModel = viewModel,
            context = context
        )
    }
}

@Composable
private fun DetailsContent(
    game: Game,
    savedState: MutableState<Boolean>,
    viewModel: DetailViewModel,
    context: Context,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        DetailTitle(
            game = game,
            savedState = savedState,
            viewModel = viewModel,
            context = context
        )

        GeneralGameInfo(game = game)

        Gap(size = 16.dp)

        Text(
            text = stringResource(R.string.copy_description),
            style = MaterialTheme.typography.titleMedium,
            color = Primary70
        )

        Text(
            text = game.description.ifBlank { "-" },
            style = MaterialTheme.typography.labelSmall,
            color = Neutral50,
        )

        Gap(size = 16.dp)

        Screenshots(urls = game.shortScreenshots)

        if (game.tags.isNotEmpty()) {
            Tags(game)
        }
    }
}

@Composable
private fun DetailTitle(
    game: Game,
    savedState: MutableState<Boolean>,
    viewModel: DetailViewModel,
    context: Context,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Text(
            text = game.name,
            style = MaterialTheme.typography.titleLarge,
            color = Primary70,
            modifier = Modifier.weight(1F)
        )

        Icon(
            imageVector = if (savedState.value) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = null,
            tint = Primary70,
            modifier = Modifier
                .size(32.dp)
                .padding(top = 4.dp)
                .clickable {
                    savedState.value = !savedState.value
                    viewModel.eventHandler(
                        BookmarkGame(
                            id = game.id,
                            bookmarked = savedState.value
                        )
                    )
                    if (savedState.value) context.showToast("Bookmarked!")
                }

        )
    }
}

@Composable
private fun ColumnScope.Tags(game: Game) {
    Gap(size = 16.dp)
    Text(
        text = "Tag",
        style = MaterialTheme.typography.bodyMedium,
        color = Neutral40
    )
    TagGroup(tag = game.tags.take(8))
}