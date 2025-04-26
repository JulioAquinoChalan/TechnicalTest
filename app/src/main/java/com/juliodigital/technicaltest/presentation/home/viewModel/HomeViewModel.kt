package com.juliodigital.technicaltest.presentation.home.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.juliodigital.technicaltest.domain.repository.ChampionsRepositoryImp
import com.juliodigital.technicaltest.presentation.home.HomeModel

class HomeViewModel : ViewModel() {

    private var _model = mutableStateOf(HomeModel())
    var model: State<HomeModel> = _model
    private val repository: ChampionsRepositoryImp = ChampionsRepositoryImp()

    init {
        getChampionByName()
    }

    fun getChampionByName() {
        repository.getChampionByName { champions ->
            champions?.let {
                _model.value = _model.value.copy(champions = it)
            }
        }
    }
}