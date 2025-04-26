package com.juliodigital.technicaltest.data.models

import com.juliodigital.technicaltest.utils.constants.Constants

data class StatsChampionDTO(
    val hp: Int? = Constants.ZERO,
    val hpPerLevel: Int? = Constants.ZERO,
    val mp: Int? = Constants.ZERO,
    val mpPerLevel: Int? = Constants.ZERO,
    val moveSpeed: Int? = Constants.ZERO,
    val armor: Int? = Constants.ZERO,
    val armorPerLevel: Double? = Constants.ZERO_DOUBLE,
    val spellBlock: Int? = Constants.ZERO,
    val spellBlockPerLevel: Double? = Constants.ZERO_DOUBLE,
    val attackRange: Int? = Constants.ZERO,
    val hpRegen: Int? = Constants.ZERO,
    val hpRegenPerLevel: Int? = Constants.ZERO,
    val mpRegen: Int? = Constants.ZERO,
    val mpRegenPerLevel: Int? = Constants.ZERO,
    val crit: Int? = Constants.ZERO,
    val critPerLevel: Int? = Constants.ZERO,
    val attackDamage: Int? = Constants.ZERO,
    val attackDamagePerLevel: Int? = Constants.ZERO,
    val attackSpeedPerLevel: Double? = Constants.ZERO_DOUBLE,
    val attackSpeed: Double? = Constants.ZERO_DOUBLE
)