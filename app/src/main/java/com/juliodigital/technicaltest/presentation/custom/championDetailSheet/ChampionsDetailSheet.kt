package com.juliodigital.technicaltest.presentation.custom.championDetailSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.juliodigital.technicaltest.domain.model.ChampionModel

@Composable
fun ChampionDetailSheet(champ: ChampionModel) {

    Column (
        modifier = Modifier
            .padding(all = 16.dp)
    ){
        Text(
            text = champ.name,
            style = MaterialTheme.typography.titleLarge,
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = champ.title,
            style = MaterialTheme.typography.titleMedium,
            fontFamily = FontFamily.Monospace
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row (
            modifier = Modifier
                .background(color = Color.Cyan.copy(alpha = 0.1f))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = champ.imageUrl,
                contentDescription = champ.name,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column (
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = champ.blurb
        )
    }
}

@Composable
private fun StatMoreValue(stat: String, value: String) {
    Column (
    ){
        Text(
            text = stat,
            style = MaterialTheme.typography.labelLarge,
            fontFamily = FontFamily.Serif
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
