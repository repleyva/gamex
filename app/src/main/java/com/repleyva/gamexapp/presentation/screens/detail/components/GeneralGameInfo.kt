package com.repleyva.gamexapp.presentation.screens.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.repleyva.core.domain.enums.ConverterDate
import com.repleyva.core.domain.extensions.convertDateTo
import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.R
import com.repleyva.gamexapp.presentation.screens.home.components.RatingBar
import com.repleyva.gamexapp.presentation.screens.home.components.TagGroup
import com.repleyva.gamexapp.presentation.ui.theme.Accent10
import com.repleyva.gamexapp.presentation.ui.theme.Accent50
import com.repleyva.gamexapp.presentation.ui.theme.Neutral40
import com.repleyva.gamexapp.presentation.ui.theme.Neutral50

@Composable
fun GeneralGameInfo(
    game: Game,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TopDetailsGame(game)
        GameInformation(game)
    }
}

@Composable
private fun TopDetailsGame(game: Game) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(36.dp)
    ) {
        MetaScoreInformation(game)
        RatingInformation(game)
    }
}

@Composable
private fun MetaScoreInformation(game: Game) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(
            text = stringResource(R.string.copy_metascore),
            style = MaterialTheme.typography.bodyMedium,
            color = Neutral40
        )

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Accent10)
        ) {
            Text(
                text = if (game.metacritic != 0) game.metacritic.toString() else "-",
                style = MaterialTheme.typography.titleSmall,
                color = Accent50,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun RatingInformation(game: Game) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.copy_rating),
            style = MaterialTheme.typography.bodyMedium,
            color = Neutral40
        )
        RatingBar(
            rating = game.rating.toFloat(),
            modifier = Modifier
                .height(16.dp)
        )
    }
}

@Composable
private fun GameInformation(game: Game) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        ReleasedInformation(game)
        GenreInformation(game)
    }
}

@Composable
private fun ReleasedInformation(game: Game) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(
            text = stringResource(R.string.copy_released),
            style = MaterialTheme.typography.bodyMedium,
            color = Neutral40
        )
        Text(
            text = game.released.convertDateTo(ConverterDate.FULL_DATE),
            style = MaterialTheme.typography.bodyMedium,
            color = Neutral50
        )
    }
}

@Composable
private fun GenreInformation(game: Game) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.copy_genre),
            style = MaterialTheme.typography.bodyMedium,
            color = Neutral40
        )
        TagGroup(tag = game.genres)
    }
}