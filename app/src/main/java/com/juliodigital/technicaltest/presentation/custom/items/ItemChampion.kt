package com.juliodigital.technicaltest.presentation.custom.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.juliodigital.technicaltest.domain.model.ChampionModel

@Composable
fun ItemChampion(champ: ChampionModel, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ChampImage(champ)
        Text(text = champ.name, fontWeight = FontWeight.Bold)
        Text(text = champ.tags.joinToString(", "))
    }
}

@Composable
private fun ChampImage(champ: ChampionModel) {
    val painter = rememberAsyncImagePainter(model = champ.imageUrl)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = champ.name,
            modifier = Modifier.fillMaxSize()
        )

        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator()
            }
            is AsyncImagePainter.State.Error -> {
                Text("Error al cargar imagen")
            }
            else -> {}
        }
    }
}