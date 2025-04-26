package com.juliodigital.technicaltest.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.juliodigital.technicaltest.presentation.AppViewModelFactory
import com.juliodigital.technicaltest.presentation.videoFolders.VideoViewModel
import com.juliodigital.technicaltest.presentation.views.loledex.LoledexScreen
import com.juliodigital.technicaltest.presentation.views.exoplayer.ExoplayerScreen
import com.juliodigital.technicaltest.ui.theme.TechnicalTestTheme
import kotlinx.coroutines.launch

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Loledex : BottomNavItem("Loledex", Icons.Default.Face, "loledex")
    object Exoplayer : BottomNavItem("Exoplayer", Icons.Default.PlayArrow, "exoplayer")
}

class HomeActivity : ComponentActivity() {
    private val factory by lazy {
        AppViewModelFactory(this@HomeActivity)
    }

    private val viewModel: HomeViewModel by viewModels { factory }
    private val videoViewModel: VideoViewModel by viewModels { factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechnicalTestTheme {
                HomeView(viewModel, videoViewModel)
            }
            videoViewModel.loadVideos()
            videoViewModel.loadFolders()
        }
    }
}

@Composable
fun HomeView(viewModel: HomeViewModel, videoViewModel: VideoViewModel) {
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
fun BottomNavigationBar(
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

