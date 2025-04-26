package com.juliodigital.technicaltest.data.models.championData

import com.juliodigital.technicaltest.domain.model.Champion
import com.juliodigital.technicaltest.domain.model.ChampionImage
import com.juliodigital.technicaltest.domain.model.ChampionInfo
import com.juliodigital.technicaltest.utils.Constants

fun ChampionDataDTO.toDomain(): List<Champion> {
    return data?.values?.map { dto ->
        Champion(
            name = dto.name ?: Constants.EMPTY_STRING,
            title = dto.title ?: Constants.EMPTY_STRING,
            tags = dto.tags ?: emptyList(),
            info = ChampionInfo(
                attack = dto.info?.attack ?: Constants.ZERO,
                defense = dto.info?.defense ?: Constants.ZERO,
                magic = dto.info?.magic ?: Constants.ZERO,
                difficulty = dto.info?.difficulty ?: Constants.ZERO
            ),
            image = ChampionImage(
                full = dto.image?.full ?: Constants.EMPTY_STRING
            )
        )
    } ?: emptyList()
}