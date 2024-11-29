package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.listatareas

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea

data class ListaUiState(
    val listaTareas: List<Tarea> = listOf()
)

data class UiStateDialogo(
    val mostrarDialogo: Boolean = false,
    val tareaABorrar: Tarea? = null
)

data class UiStateFiltro(
    val filtroEstado: String,

)