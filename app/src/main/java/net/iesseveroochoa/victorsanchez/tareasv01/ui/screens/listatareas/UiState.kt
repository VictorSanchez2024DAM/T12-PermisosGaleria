package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.listatareas

import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea

/**
 * Clase de estado para la lista de tareas
 */
// Clase de estado para la lista de tareas
data class ListaUiState(
    val listaTareas: List<Tarea> = listOf()
)

// Clase de estado para el dialogo de confirmación
data class UiStateDialogo(
    val mostrarDialogo: Boolean = false,
    val tareaABorrar: Tarea? = null
)

// Clase de estado para el filtro de estado
data class UiStateFiltro(
    val filtroEstado: String,

)

// Clase de estado para el switch
data class UiStateSinPagar(
    val switchSinPagar: Boolean = false,

)