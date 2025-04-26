package com.juliodigital.technicaltest.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliodigital.technicaltest.domain.repository.ChampionsRepositoryImp
import com.juliodigital.technicaltest.domain.userCase.GetAllVideosUseCase
import kotlinx.coroutines.launch

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
