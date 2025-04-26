package com.juliodigital.technicaltest.presentation.home

import com.juliodigital.technicaltest.domain.model.ChampionModel

data class HomeModel(
    var champions: List<ChampionModel>? = null,
)