package net.iesseveroochoa.victorsanchez.tareasv01.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea
import net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.tarea.TareaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar(
    viewModel: TareaViewModel,
    onBackClick: () -> Unit = {},
    tareaId: Long? = null
) {
    var tarea: Tarea? = null
    // Obtenemos el valor de uiStateTarea de manera reactiva usando collectAsState
    val uiStateTarea by viewModel.uiStateTarea.collectAsState()

    // Construimos el título usando stringResource para convertir el ID en String
    val title = if (uiStateTarea.esTareaNueva) {
        stringResource(R.string.nueva_tarea)
    } else {
        stringResource(R.string.editar_tarea) + " " + tareaId.toString()
    }

    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.volver))
            }
        }
    )
}
