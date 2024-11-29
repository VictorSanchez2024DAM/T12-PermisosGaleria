package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.listatareas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea
import net.iesseveroochoa.victorsanchez.tareasv01.data.repository.Repository


class ListaTareasViewModel(application: Application) : AndroidViewModel(application) {

    // cogemos el contexto de la aplicación
    private val context = application.applicationContext

    // Estado del dialogo
    private val _uiState = MutableStateFlow(UiStateDialogo())
    val dialogoConfirmacionUiState: StateFlow<UiStateDialogo> = _uiState


    // Lista de estados
    val listaFiltrado = context.resources.getStringArray(R.array.filtro_estado).toList()

    // Estado del filtro
    private val _uiStateFiltro = MutableStateFlow(UiStateFiltro(
        filtroEstado = listaFiltrado[3]
    ))
    val filtroUiState: StateFlow<UiStateFiltro> = _uiStateFiltro

    //Estado de la lista dependerá del filtro de estado
    val listaTareasUiState: StateFlow<ListaUiState> = _uiStateFiltro.flatMapLatest {
        //cuando el filtro cambia, cambia la Select
            uiStateFiltro ->
        if (uiStateFiltro.filtroEstado == listaFiltrado[3])//todas
            Repository.getAllTareas()
        else//filtro estado
            Repository.getTareasPorEstado(listaFiltrado.indexOf(uiStateFiltro.filtroEstado))

    }
        .map{ ListaTareas ->
            ListaUiState(
                listaTareas = ListaTareas
            )

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ListaUiState()
        )

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

    // Cambia el filtro de estado en el ViewModel
    fun onCheckedChangeFiltroEstado(nuevoFiltroEstado: String) {
        _uiStateFiltro.value=_uiStateFiltro.value.copy(
            filtroEstado = nuevoFiltroEstado
        )
    }
}