package com.juliodigital.technicaltest.domain.repository

import com.juliodigital.technicaltest.domain.model.ChampionModel
import com.juliodigital.technicaltest.data.repository.ChampionsRepositoryInterface
import com.juliodigital.technicaltest.data.models.championData.ChampionDataDTO
import com.juliodigital.technicaltest.data.models.championData.toDomain
import com.juliodigital.technicaltest.data.remote.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChampionsRepositoryImp: ChampionsRepositoryInterface {

    var api = RetrofitInstance.api

    override fun getChampionByName(call: (List<ChampionModel>?) -> Unit) {
        api.getChampions()
            .enqueue(object: Callback<ChampionDataDTO> {
                override fun onResponse(
                    p0: Call<ChampionDataDTO?>,
                    response: Response<ChampionDataDTO?>
                ) {
                    response.body()?.let { championDataWrapper ->
                        call(championDataWrapper.toDomain())
                    } ?: {
                        call(null)
                    }
                }

                override fun onFailure(
                    p0: Call<ChampionDataDTO?>,
                    p1: Throwable
                ) {
                   call(null)
                }
            })
    }
}