package com.juliodigital.technicaltest.domain.model

data class ChampionModel(
    val name: String,
    val title: String,
    val blurb: String,
    val tags: List<String>,
    val info: ChampionInfoModel,
    val image: ChampionImageModel
) {
    val imageUrl: String
        get() = "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/${image.full}"
}