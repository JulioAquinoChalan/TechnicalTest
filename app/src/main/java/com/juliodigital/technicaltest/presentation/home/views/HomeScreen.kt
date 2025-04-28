package com.juliodigital.technicaltest.presentation.home.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.juliodigital.technicaltest.presentation.home.BottomNavItem
import com.juliodigital.technicaltest.presentation.home.viewModel.HomeViewModel
import com.juliodigital.technicaltest.presentation.videoFolders.viewModel.VideoViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel, videoViewModel: VideoViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val items = listOf(
        BottomNavItem.Loledex,
        BottomNavItem.Exoplayer
    )
    val pagerState = rememberPagerState(initialPage = 0, pageCount = {items.size})

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) { page ->
            when (items[page].route) {
                BottomNavItem.Loledex.route -> LoledexScreen(viewModel)
                BottomNavItem.Exoplayer.route -> ExoplayerScreen(videoViewModel)
            }
        }

        BottomNavigationBar (
            items = items,
            currentPage = pagerState.currentPage,
            onItemSelected = { index ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }
        )
    }
}

@Composable
private fun BottomNavigationBar(
    items: List<BottomNavItem>,
    currentPage: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar() {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, maxLines = 1) },
                selected = currentPage == index,
                onClick = {
                    onItemSelected(index)
                }
            )
        }
    }
}
