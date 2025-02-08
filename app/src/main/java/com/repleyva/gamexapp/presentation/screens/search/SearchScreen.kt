package com.repleyva.gamexapp.presentation.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.repleyva.core.domain.model.Game
import com.repleyva.gamexapp.R
import com.repleyva.gamexapp.presentation.screens.home.components.GameItem
import com.repleyva.gamexapp.presentation.screens.search.SearchScreenEvent.OnSearchQueryChange
import com.repleyva.gamexapp.presentation.ui.theme.Neutral10
import com.repleyva.gamexapp.presentation.ui.theme.Neutral40
import com.repleyva.gamexapp.presentation.ui.theme.Primary50

@Composable
fun SearchScreen(
    state: SearchScreenState,
    eventHandler: (SearchScreenEvent) -> Unit,
    onDetailScreen: (game: Game) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .testTag("searchScreen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = stringResource(R.string.copy_search_title),
            style = MaterialTheme.typography.headlineMedium,
            color = Primary50,
            modifier = Modifier.padding(top = 36.dp, bottom = 12.dp)
        )

        InputSearch(
            state = state,
            eventHandler = eventHandler
        )

        SearchResults(
            state = state,
            onDetailScreen = onDetailScreen
        )
    }

}

@Composable
private fun InputSearch(
    state: SearchScreenState,
    eventHandler: (SearchScreenEvent) -> Unit,
) {
    TextField(
        value = state.query,
        onValueChange = { eventHandler(OnSearchQueryChange(it)) },
        textStyle = MaterialTheme.typography.bodyLarge,
        placeholder = {
            Text(
                text = stringResource(R.string.copy_search_placeholder),
                color = Neutral40,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("placeholderText")
            )
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "searchIcon",
                modifier = Modifier.size(32.dp)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "sendIcon",
                tint = Primary50,
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Neutral10,
            unfocusedContainerColor = Neutral10,
            disabledContainerColor = Neutral10,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledLabelColor = Color.Blue,
        ),
        maxLines = 1,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .testTag("searchInput")
    )
}

@Composable
private fun SearchResults(
    state: SearchScreenState,
    onDetailScreen: (game: Game) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("searchResultList"),
        contentPadding = PaddingValues(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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