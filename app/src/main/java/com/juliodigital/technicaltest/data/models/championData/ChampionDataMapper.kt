package com.juliodigital.technicaltest.data.models.championData

import com.juliodigital.technicaltest.domain.model.ChampionModel
import com.juliodigital.technicaltest.domain.model.ChampionImageModel
import com.juliodigital.technicaltest.domain.model.ChampionInfoModel
import com.juliodigital.technicaltest.utils.constants.Constants

fun ChampionDataDTO.toDomain(): List<ChampionModel> {
    return data?.values?.map { dto ->
        ChampionModel(
            name = dto.name ?: Constants.EMPTY_STRING,
            title = dto.title ?: Constants.EMPTY_STRING,
            blurb = dto.blurb ?: Constants.EMPTY_STRING,
            tags = dto.tags ?: emptyList(),
            info = ChampionInfoModel(
                attack = dto.info?.attack ?: Constants.ZERO,
                defense = dto.info?.defense ?: Constants.ZERO,
                magic = dto.info?.magic ?: Constants.ZERO,
                difficulty = dto.info?.difficulty ?: Constants.ZERO
            ),
            image = ChampionImageModel(
                full = dto.image?.full ?: Constants.EMPTY_STRING
            )
        )
    } ?: emptyList()
}