package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.tarea

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope

// Clase que guarda el estado de la tarea
data class UiStateTarea(
    val categoria: String = "",
    val prioridad: String = "",
    val checked: Boolean = false,
    val estado: String = "",
    val valoracion: Int = 0,
    val tecnico: String = "",
    val descripcion: String = "",
    val colorFondo: Color = Color.Transparent,
    val esFormularioValido: Boolean = false,
    val mostrarDialogo: Boolean = false,
    val esTareaNueva: Boolean = true,
    val snackbarHostState: SnackbarHostState,
    val scope: CoroutineScope
)