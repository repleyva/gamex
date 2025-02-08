package com.repleyva.gamexapp.presentation.nav

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.repleyva.gamexapp.presentation.ui.theme.Neutral50
import com.repleyva.gamexapp.presentation.ui.theme.Primary50

@Composable
fun BottomNavigationBar(
    selected: Int,
    items: List<BottomBarDestination>,
    modifier: Modifier = Modifier,
    onItemClick: (BottomBarDestination) -> Unit,
) {

    NavigationBar(
        modifier = modifier.testTag("bottomNavigatorTag")
    ) {
        items.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                selected = index == selected,
                onClick = {
                    if (index != selected) {
                        onItemClick(bottomNavItem)
                    }
                },
                label = {
                    Text(
                        text = stringResource(bottomNavItem.label),
                        color = if (index == selected) Primary50 else Neutral50
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(bottomNavItem.icon),
                        contentDescription = stringResource(bottomNavItem.label),
                        tint = if (index == selected) Primary50 else Neutral50,
                        modifier = Modifier.size(26.dp)
                    )
                }
            )
        }
    }
}