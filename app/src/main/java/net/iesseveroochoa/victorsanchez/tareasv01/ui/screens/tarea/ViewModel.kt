package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.tarea

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.ui.theme.ColorPrioridadAlta

class TareaViewModel(application: Application): AndroidViewModel(application) {
    private val context = application.applicationContext

    val listaPrioridad = context.resources.getStringArray(R.array.prioridad).toList()
    val PRIORIDAD_ALTA = listaPrioridad[0]

    private val _uiStateTarea = MutableStateFlow(UiStateTarea(
        prioridad = listaPrioridad[2]
    ))

    val uiStateTarea: StateFlow<UiStateTarea> = _uiStateTarea.asStateFlow()

    fun onValueChangePrioridad(nuevaPrioridad: String){
        var colorFondo: Color
        if (PRIORIDAD_ALTA==nuevaPrioridad)
            colorFondo = ColorPrioridadAlta

        else
            colorFondo = Color.Transparent

        _uiStateTarea.value = _uiStateTarea.value.copy(
            prioridad = nuevaPrioridad,
            colorFondo = colorFondo
        )
    }
}

