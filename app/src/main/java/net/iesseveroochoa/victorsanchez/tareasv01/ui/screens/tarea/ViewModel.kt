package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.tarea

import android.app.Application
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.ui.theme.ColorPrioridadAlta

// Clase que guarda el estado de la tarea y gestiona los cambios de estado
class TareaViewModel(application: Application): AndroidViewModel(application) {
    private val context = application.applicationContext

    // lista de prioridades y categorias y radiobutton
    val listaPrioridad = context.resources.getStringArray(R.array.prioridad).toList()
    val PRIORIDAD_ALTA = listaPrioridad[0]
    val categorias = context.resources.getStringArray(R.array.categorias).toList()
    val radioOptions = context.resources.getStringArray(R.array.estado).toList()

    // guarda el estado de la tarea
    private val _uiStateTarea = MutableStateFlow(
        UiStateTarea(
            prioridad = listaPrioridad[2],
            categoria = categorias[0],
            checked = false,
            estado = radioOptions[0],
            valoracion = 0,
            tecnico = "",
            descripcion = "",
            esFormularioValido = false,
            mostrarDialogo = false,
            snackbarHostState = SnackbarHostState(),
            scope = viewModelScope
        )
    )

    val uiStateTarea: StateFlow<UiStateTarea> = _uiStateTarea.asStateFlow()

    // guarda la prioridad y el color de fondo segun la prioridad
    fun onValueChangePrioridad(nuevaPrioridad: String) {
        var colorFondo: Color
        if (PRIORIDAD_ALTA == nuevaPrioridad)
            colorFondo = ColorPrioridadAlta
        else
            colorFondo = Color.Transparent

        _uiStateTarea.value = _uiStateTarea.value.copy(
            prioridad = nuevaPrioridad,
            colorFondo = colorFondo
        )
    }
    // guarda la categoria
    fun onValueChangeCategoria(nuevaCategoria: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            categoria = nuevaCategoria
        )
    }
    // guarda el estado
    fun onCheckedChange(nuevoChecked: Boolean) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            checked = nuevoChecked
        )
    }
    // guarda el estado
    fun onValueChangeEstado(nuevoEstado: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            estado = nuevoEstado
        )
    }
    // guarda la valoracion
    fun onRatingChanged(nuevaValoracion: Int) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            valoracion = nuevaValoracion
        )

    }
    // guarda el tecnico
    fun onValueChangeTecnico(nuevoTecnico: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            tecnico = nuevoTecnico,
            esFormularioValido = nuevoTecnico.isNotBlank() && _uiStateTarea.value.descripcion.isNotBlank()
        )

    }
    //guarda la descripcion
    fun onValueChangeDescripcion(nuevaDescripcion: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            descripcion = nuevaDescripcion,
            esFormularioValido = nuevaDescripcion.isNotBlank() && _uiStateTarea.value.tecnico.isNotBlank()
        )
    }
    //muestra el dialogo
    fun onGuardar() {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            mostrarDialogo = true
        )
    }
    //guardará los cambios, por el momento solo cierra el dialogo
    fun onConfirmarDialogoGuardar() {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            mostrarDialogo = false
        )
    }
    //cierra el dialogo
    fun onCancelarDialogoGuardar() {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            mostrarDialogo = false
        )
    }
}

