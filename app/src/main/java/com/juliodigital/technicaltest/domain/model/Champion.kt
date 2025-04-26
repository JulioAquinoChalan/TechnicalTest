package com.juliodigital.technicaltest.domain.model

data class Champion(
    val name: String,
    val title: String,
    val tags: List<String>,
    val info: ChampionInfo,
    val image: ChampionImage
) {
    val imageUrl: String
        get() = "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${image.full}"
}