package com.juliodigital.technicaltest.data.repository

import com.juliodigital.technicaltest.domain.model.Champion

interface ChampionsRepositoryInterface {
    fun getChampionByName(call: (List<Champion>?) -> Unit)
}