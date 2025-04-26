package com.juliodigital.technicaltest.presentation.views.loledex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.juliodigital.technicaltest.domain.model.Champion

@Composable
fun ItemChampion(champ: Champion, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AsyncImage(
            model = champ.imageUrl,
            contentDescription = champ.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Text(text = champ.name, fontWeight = FontWeight.Bold)
        Text(text = champ.tags.joinToString(", "))
    }
}

@Composable
fun ChampionDetailSheet(champ: Champion) {
    Column {
        Text(text = champ.name)
        Text(text = champ.title)
        Row (Modifier.fillMaxWidth().padding(16.dp)){
            AsyncImage(
                model = champ.imageUrl,
                contentDescription = champ.name,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column (
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ){
                StatMoreValue("Attack", champ.info.attack.toString())
                HorizontalDivider()
                StatMoreValue("Defense", champ.info.defense.toString())
                HorizontalDivider()
                StatMoreValue("Magic", champ.info.magic.toString())
                HorizontalDivider()
                StatMoreValue("Difficulty", champ.info.difficulty.toString())
            }
        }
    }
}

@Composable
fun StatMoreValue(stat: String, value: String) {
    Column (
    ){
        Text(
            text = stat,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
