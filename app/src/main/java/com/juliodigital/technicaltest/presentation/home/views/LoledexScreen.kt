package com.juliodigital.technicaltest.presentation.home.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juliodigital.technicaltest.domain.model.ChampionModel
import com.juliodigital.technicaltest.presentation.custom.items.ItemChampion
import com.juliodigital.technicaltest.presentation.custom.championDetailSheet.ChampionDetailSheet
import com.juliodigital.technicaltest.presentation.home.viewModel.HomeViewModel
import kotlinx.coroutines.launch
import kotlin.collections.chunked

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoledexScreen(viewModel: HomeViewModel) {
    val model by viewModel.model
    val rows by remember(model.champions) {
        mutableStateOf(model.champions?.chunked(2) ?: emptyList())
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var selectedChampion by remember { mutableStateOf<ChampionModel?>(null) }
    var showSheet by remember { mutableStateOf(false) }

    LazyColumn(Modifier.padding(10.dp)) {
        items (rows) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowItems.forEach {
                    ItemChampion(
                        champ = it,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                selectedChampion = it
                                showSheet = true
                                coroutineScope.launch {
                                    sheetState.show()
                                }
                            }
                    )
                }
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }

    if (showSheet && selectedChampion != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
                selectedChampion = null
            },
            sheetState = sheetState
        ) {
            ChampionDetailSheet(champ = selectedChampion!!)
        }
    }
}