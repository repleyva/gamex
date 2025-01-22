package com.repleyva.gamexapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.repleyva.gamexapp.R
import com.repleyva.gamexapp.presentation.extensions.shimmerEffect

@Composable
fun CoilImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {

    var showShimmer by rememberSaveable { mutableStateOf(true) }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .decoderFactory(SvgDecoder.Factory())
            .build(),
        contentDescription = "image",
        error = painterResource(id = R.drawable.ic_placeholder),
        contentScale = contentScale,
        modifier = modifier
            .then(
                if (showShimmer) Modifier.shimmerEffect()
                else Modifier
            ),
        onLoading = { showShimmer = true },
        onSuccess = { showShimmer = false },
        onError = { showShimmer = false },
    )
}