package com.juliodigital.technicaltest.presentation.main

import com.juliodigital.technicaltest.domain.model.Champion
import com.juliodigital.technicaltest.domain.model.VideoItem

data class HomeModel(
    var champions: List<Champion>? = null,
)