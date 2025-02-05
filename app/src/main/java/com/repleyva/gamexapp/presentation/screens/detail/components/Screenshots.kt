package com.repleyva.gamexapp.presentation.screens.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.repleyva.gamexapp.R
import com.repleyva.gamexapp.presentation.components.CoilImage
import com.repleyva.gamexapp.presentation.ui.theme.Primary70

@Composable
fun Screenshots(
    urls: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = stringResource(R.string.copy_screenshots),
            style = MaterialTheme.typography.titleMedium,
            color = Primary70
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("screenshots")
        ) {
            items(
                items = urls,
                key = { it }
            ) {
                CoilImage(
                    url = it,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(200.dp)
                )
            }
        }
    }
}