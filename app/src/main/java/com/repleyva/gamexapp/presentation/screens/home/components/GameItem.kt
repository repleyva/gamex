package com.repleyva.gamexapp.presentation.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.repleyva.core.domain.enums.ConverterDate
import com.repleyva.core.domain.extensions.convertDateTo
import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.presentation.components.CoilImage
import com.repleyva.gamexapp.presentation.extensions.shimmerEffect
import com.repleyva.gamexapp.presentation.ui.theme.Neutral40
import com.repleyva.gamexapp.presentation.ui.theme.Neutral50
import com.repleyva.gamexapp.presentation.ui.theme.Neutral60
import com.repleyva.gamexapp.presentation.ui.theme.Primary80
import com.repleyva.gamexapp.presentation.ui.theme.Yellow

@Composable
fun GameItem(
    game: Game,
    modifier: Modifier = Modifier,
    onEvent: (Game) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable {
                onEvent(game)
            },
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        CoilImage(
            url = game.backgroundImage,
            modifier = Modifier
                .fillMaxHeight()
                .width(85.dp)
                .clip(RoundedCornerShape(5.dp))
        )

        ItemBody(game)
    }
}

@Composable
private fun ItemBody(game: Game) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = game.name,
            style = MaterialTheme.typography.bodyLarge,
            color = Primary80
        )

        Rating(game)

        TagGroup(tag = game.genres, isLimited = true)

        DateRange(game)
    }
}

@Composable
private fun Rating(game: Game) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = Yellow,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = "${game.rating}/5",
            style = MaterialTheme.typography.labelMedium,
            color = Neutral60
        )
    }
}

@Composable
private fun DateRange(game: Game) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
            tint = Neutral40,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = game.released.convertDateTo(ConverterDate.FULL_DATE),
            style = MaterialTheme.typography.labelSmall,
            color = Neutral50
        )
    }
}

@Composable
fun GameItemLoading(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .shimmerEffect()
    )
}