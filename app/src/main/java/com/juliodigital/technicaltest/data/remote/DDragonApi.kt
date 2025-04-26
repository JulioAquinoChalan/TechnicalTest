package com.juliodigital.technicaltest.data.remote

import com.juliodigital.technicaltest.data.models.championData.ChampionDataDTO
import retrofit2.Call
import retrofit2.http.GET

interface DDragonApi {
    @GET("cdn/13.24.1/data/en_US/champion.json")
    fun getChampions(): Call<ChampionDataDTO>
}
