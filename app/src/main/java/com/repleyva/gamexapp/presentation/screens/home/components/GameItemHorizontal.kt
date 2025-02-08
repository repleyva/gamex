package com.repleyva.gamexapp.presentation.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.presentation.components.CoilImage
import com.repleyva.gamexapp.presentation.components.Gap
import com.repleyva.gamexapp.presentation.extensions.shimmerEffect
import com.repleyva.gamexapp.presentation.ui.theme.Primary80

@Composable
fun GameItemHorizontal(
    game: Game,
    modifier: Modifier = Modifier,
    onDetailScreen: (game: Game) -> Unit,
) {
    Column(
        modifier = modifier
            .width(140.dp)
            .clickable {
                onDetailScreen(game)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        CoilImage(
            url = game.backgroundImage,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(5.dp))
        )

        Gap(size = 4.dp)

        Text(
            text = game.name,
            style = MaterialTheme.typography.bodyLarge,
            color = Primary80,
            textAlign = TextAlign.Center
        )

        RatingBar(
            rating = game.rating.toFloat(),
            modifier = Modifier.height(10.dp)
        )
    }
}

@Composable
fun GameItemHorizontalLoading() {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(200.dp)
            .shimmerEffect()
            .testTag("itemHorizontalShimmerTag")
    )
}