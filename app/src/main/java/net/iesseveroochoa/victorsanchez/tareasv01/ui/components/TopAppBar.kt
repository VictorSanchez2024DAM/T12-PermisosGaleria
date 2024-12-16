package net.iesseveroochoa.victorsanchez.tareasv01.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea
import net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.tarea.TareaViewModel

/**
 * Crea la barra de navegación de la tarea
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBarT(
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
    }
    else {
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

/**
 * Crea la barra de navegación de la lista de tareas
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBarL(
    onBackClick: () -> Unit = {},
    canGoBack: Boolean,
    listaAcciones: List<ActionItem> = listOf(),
    listaAccionesOverflow: List<ActionItem> = listOf(),
    modifier: Modifier = Modifier
) {

    // Construimos el título usando stringResource para convertir el ID en String
    val title = stringResource(R.string.lista_de_tareas)

    TopAppBar(
        title = {
            Text(text = title)
        },
        modifier = modifier,
        navigationIcon = {
            //si es la primera pantalla no se muestra el botón de navegación
            if (canGoBack) {
                //lambda que iría a la pantalla anterior
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,

                        contentDescription = stringResource(R.string.volver)
                    )
                }
            }
        },
        //Acciones de la barra de navegación
        actions = {
            if (listaAcciones.isNotEmpty())
                menuAction(listaAcciones)

            //overflow menu
            val (isExpanded, setExpanded) = remember { mutableStateOf(false) }
            if (listaAccionesOverflow.isNotEmpty())
                OverflowMenuAction(isExpanded, setExpanded, listaAccionesOverflow)
        }
    )
}


/**
 * Nos permite crear las acciones del menú de la TopAppBar
 */
data class ActionItem(
    val name: String,
    //los elementos del menú overflow pueden no tener icono
    val icon: ImageVector? = null,
    val action: () -> Unit,
    //val order: Int
)

/**
 * Acciones de la barra de navegación.
 */
@Composable
private fun menuAction(
    options: List<ActionItem>
) {
    options.forEach { // (2) Crear action items desde lista
        IconButton(onClick = it.action) {
            Icon(imageVector = it.icon!!, contentDescription = it.name)
        }
    }
}

/**
 * Acciones del overflow menu
 */
@Composable
private fun OverflowMenuAction(
    expanded: Boolean,
    setExpanded: (Boolean) -> Unit,
    options: List<ActionItem>
) {
    IconButton(onClick = { setExpanded(true) }) {
        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Ver más")

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { setExpanded(false) },
            offset = DpOffset(x = 0.dp, y = 4.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { //si tiene icono
                        if (option.icon != null) {
                            Row(
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = option.icon,
                                    contentDescription = option.name,
                                    modifier = Modifier.scale(0.75f)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = option.name)
                            }
                        }else//sin icono
                            Text(text = option.name)
                    },
                    onClick = {
                        option.action()
                        setExpanded(false)
                    }
                )
            }
        }
    }
}
