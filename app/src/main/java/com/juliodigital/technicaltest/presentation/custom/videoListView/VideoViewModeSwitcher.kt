package com.juliodigital.technicaltest.presentation.custom.videoListView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juliodigital.technicaltest.domain.enum.VideoViewMode

@Composable
fun VideoViewModeSwitcher(
    currentMode: VideoViewMode,
    onModeChange: (VideoViewMode) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFFE0E0E0))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val selectedColor = Color.White
        val unselectedColor = Color.Transparent
        val textSelectedColor = Color.Black
        val textUnselectedColor = Color.DarkGray

        ToggleButton(
            text = "Todos los videos",
            selected = currentMode == VideoViewMode.ALL_VIDEOS,
            onClick = { onModeChange(VideoViewMode.ALL_VIDEOS) },
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
            textSelectedColor = textSelectedColor,
            textUnselectedColor = textUnselectedColor,
            modifier = Modifier.weight(1f)
        )

        ToggleButton(
            text = "Por carpetas",
            selected = currentMode == VideoViewMode.BY_FOLDER,
            onClick = { onModeChange(VideoViewMode.BY_FOLDER) },
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
            textSelectedColor = textSelectedColor,
            textUnselectedColor = textUnselectedColor,
            modifier = Modifier.weight(1f)
        )

        ToggleButton(
            text = "De Youtube",
            selected = currentMode == VideoViewMode.BY_Youtube,
            onClick = { onModeChange(VideoViewMode.BY_Youtube) },
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
            textSelectedColor = textSelectedColor,
            textUnselectedColor = textUnselectedColor,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color,
    unselectedColor: Color,
    textSelectedColor: Color,
    textUnselectedColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(if (selected) selectedColor else unselectedColor)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) textSelectedColor else textUnselectedColor,
            fontSize = 14.sp
        )
    }
}