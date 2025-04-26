package com.juliodigital.technicaltest.data.models

import com.juliodigital.technicaltest.utils.constants.Constants

data class InfoChampionDTO (
    val attack: Int? = Constants.ZERO,
    val defense: Int? = Constants.ZERO,
    val magic: Int? = Constants.ZERO,
    val difficulty: Int? = Constants.ZERO,
)