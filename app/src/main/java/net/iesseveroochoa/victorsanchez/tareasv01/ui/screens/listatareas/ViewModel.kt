package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.listatareas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea
import net.iesseveroochoa.victorsanchez.tareasv01.data.repository.Repository

/**
 * ViewModel para la lista de tareas
 */
class ListaTareasViewModel(application: Application) : AndroidViewModel(application) {

    // cogemos el contexto de la aplicación
    private val context = application.applicationContext

    // Estado del dialogo
    private val _uiStateDialogo = MutableStateFlow(UiStateDialogo())
    val dialogoConfirmacionUiState: StateFlow<UiStateDialogo> = _uiStateDialogo


    // Lista de estados
    val listaFiltrado = context.resources.getStringArray(R.array.filtro_estado).toList()

    // Estado del filtro
    private val _uiStateFiltro = MutableStateFlow(UiStateFiltro(
        filtroEstado = listaFiltrado[3]
    ))
    val filtroUiState: StateFlow<UiStateFiltro> = _uiStateFiltro

    // Estado del switch
    private val _uiStateSinPagar = MutableStateFlow(UiStateSinPagar())
    val uiStateSinPagar: StateFlow<UiStateSinPagar> = _uiStateSinPagar

    val listaTareasUiState: StateFlow<ListaUiState> = combine(
        _uiStateFiltro,
        _uiStateSinPagar
    ) { uiStateFiltro, uiStateSinPagar ->
        when {
            uiStateSinPagar.switchSinPagar && uiStateFiltro.filtroEstado == listaFiltrado[3] -> Repository.getTareasSinPagar()
            uiStateSinPagar.switchSinPagar -> Repository.getTareasPorEstadoYNoPagado(listaFiltrado.indexOf(uiStateFiltro.filtroEstado))
            uiStateFiltro.filtroEstado == listaFiltrado[3] -> Repository.getAllTareas()
            else -> Repository.getTareasPorEstado(listaFiltrado.indexOf(uiStateFiltro.filtroEstado))
        }
    }.flatMapLatest { it }
        .map { ListaTareas -> ListaUiState(listaTareas = ListaTareas) }
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
        _uiStateDialogo.value = _uiStateDialogo.value.copy(
            mostrarDialogo = true,
            tareaABorrar = tarea
        )
    }

    // Cancela el dialogo para borrar una tarea
    fun cancelarDialogo() {
        _uiStateDialogo.value = _uiStateDialogo.value.copy(
            mostrarDialogo = false,
            tareaABorrar = null
        )
    }

    // Borra la tarea actual
    fun aceptarDialogo() {
        val tarea = _uiStateDialogo.value.tareaABorrar ?: return
        delTarea(tarea)
        cancelarDialogo()
    }

    // Cambia el filtro de estado en el ViewModel
    fun onCheckedChangeFiltroEstado(nuevoFiltroEstado: String) {
        _uiStateFiltro.value=_uiStateFiltro.value.copy(
            filtroEstado = nuevoFiltroEstado
        )
    }

    // Cambia el estado del switch
    fun onSwitchSinPagarChanged(isChecked: Boolean) {
        _uiStateSinPagar.value = _uiStateSinPagar.value.copy(switchSinPagar = isChecked)
    }
}