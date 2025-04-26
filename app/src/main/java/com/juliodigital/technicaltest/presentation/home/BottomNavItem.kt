package com.juliodigital.technicaltest.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Loledex : BottomNavItem("Loledex", Icons.Default.Face, "loledex")
    object Exoplayer : BottomNavItem("Exoplayer", Icons.Default.PlayArrow, "exoplayer")
}