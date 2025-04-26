package com.juliodigital.technicaltest.data.models

import com.juliodigital.technicaltest.utils.constants.Constants

data class ImageChampionDTO (
    val full: String? = Constants.EMPTY_STRING,
    val sprite: String? = Constants.EMPTY_STRING,
    val group: String? = Constants.EMPTY_STRING,
    val x: Int? = Constants.ZERO,
    val y: Int? = Constants.ZERO,
    val w: Int? = Constants.ZERO,
    val h: Int? = Constants.ZERO,
)