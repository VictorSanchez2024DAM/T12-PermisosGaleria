package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.listatareas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import net.iesseveroochoa.victorsanchez.tareasv01.data.repository.Repository

class ListaTareasViewModel() : ViewModel() {
    val listaTareasUiState : StateFlow<ListaUiState> =
    //transformamos el flow de tareas en el Stateflow de ListaUiState
        Repository.getAllTareas().map {ListaUiState(it)}.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ListaUiState()
        )
}