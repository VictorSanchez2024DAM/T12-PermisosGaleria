package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.listatareas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea
import net.iesseveroochoa.victorsanchez.tareasv01.data.repository.Repository

class ListaTareasViewModel() : ViewModel() {
    val listaTareasUiState : StateFlow<ListaUiState> =
    //transformamos el flow de tareas en el Stateflow de ListaUiState
        Repository.getAllTareas().map {ListaUiState(it)}.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ListaUiState()
        )
    // Estado del dialogo
    private val _uiState = MutableStateFlow(UiStateDialogo())
    val dialogoConfirmacionUiState: StateFlow<UiStateDialogo> = _uiState

    /**
     * Borra una tarea de la base de datos
     */
    fun delTarea(tarea: Tarea) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.delTarea(tarea)
        }
    }

    // Muestra el dialogo para borrar una tarea
    fun onMostrarDialogoBorrar(tarea: Tarea) {
        _uiState.value = _uiState.value.copy(
            mostrarDialogo = true,
            tareaABorrar = tarea
        )
    }

    // Cancela el dialogo para borrar una tarea
    fun cancelarDialogo() {
        _uiState.value = _uiState.value.copy(
            mostrarDialogo = false,
            tareaABorrar = null
        )
    }

    // Borra la tarea actual
    fun aceptarDialogo() {
        val tarea = _uiState.value.tareaABorrar ?: return
        delTarea(tarea)
        cancelarDialogo()
    }
}