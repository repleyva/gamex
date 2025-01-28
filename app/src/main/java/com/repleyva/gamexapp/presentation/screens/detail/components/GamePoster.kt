package com.repleyva.gamexapp.presentation.screens.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.R
import com.repleyva.gamexapp.presentation.components.CoilImage
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent
import com.repleyva.gamexapp.presentation.ui.theme.Primary70

@Composable
fun GamePoster(
    game: Game,
    modifier: Modifier = Modifier,
    onEvent: (DetailScreenEvent) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {

        CoilImage(
            url = game.backgroundImage,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2F))
        )

        TopBar(
            onEvent = onEvent,
            game = game
        )

        if (!game.trailerUrl.isNullOrEmpty()) {
            ActionPlayTrailer(
                game = game,
                onEvent = onEvent,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun TopBar(
    onEvent: (DetailScreenEvent) -> Unit,
    game: Game,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 18.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onEvent(DetailScreenEvent.NavigateBack)
                }
        )

        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onEvent(DetailScreenEvent.ShareGame(game))
                }
        )
    }
}

@Composable
private fun ActionPlayTrailer(
    modifier: Modifier,
    game: Game,
    onEvent: (DetailScreenEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.White, CircleShape)
                .clickable {
                    game.trailerUrl?.let {
                        onEvent(DetailScreenEvent.PlayTrailer(it))
                    }
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Primary70),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        }

        Text(
            text = stringResource(id = R.string.action_play_trailer),
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
    }
}