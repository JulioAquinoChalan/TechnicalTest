package com.juliodigital.technicaltest.data.repository

import com.juliodigital.technicaltest.domain.model.ChampionModel

interface ChampionsRepositoryInterface {
    fun getChampionByName(call: (List<ChampionModel>?) -> Unit)
}