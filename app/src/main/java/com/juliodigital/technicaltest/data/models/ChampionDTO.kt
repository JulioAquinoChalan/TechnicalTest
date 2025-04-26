package com.juliodigital.technicaltest.data.models

import com.juliodigital.technicaltest.utils.constants.Constants

data class ChampionDTO (
    val id: String? = Constants.EMPTY_STRING,
    val key: String? = Constants.EMPTY_STRING,
    val name: String? = Constants.EMPTY_STRING,
    val title: String? = Constants.EMPTY_STRING,
    val blurb: String? = Constants.EMPTY_STRING,
    val info: InfoChampionDTO? = null,
    val image: ImageChampionDTO? = null,
    val tags: List<String>? = emptyList(),
    val stats: StatsChampionDTO? = null,
)