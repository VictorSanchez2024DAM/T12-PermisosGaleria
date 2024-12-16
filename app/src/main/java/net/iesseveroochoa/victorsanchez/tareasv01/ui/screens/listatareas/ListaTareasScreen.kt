package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.listatareas

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.iesseveroochoa.victorsanchez.tareasv01.R
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.ActionItem
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.DialogoDeConfirmacion
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.ItemCard
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.basicRadioButton
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.topAppBarL


// Composable para mostrar la lista de tareas en la pantalla
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaTareasScreen(
    viewModel: ListaTareasViewModel = viewModel(),
    onNuevaTareaClick: () -> Unit,
    onTareaClick: (Long) -> Unit,
) {

    // Creamos el snackbarHostState
    val snackbarHostState = remember { SnackbarHostState() }
    // Creamos el scope para las corrutinas
    val scope = rememberCoroutineScope()
    // Creamos la función para mostrar el snackbar
    val muestraSnackBar: (String, SnackbarDuration) -> Unit = { mensaje, duration ->
        scope.launch {
            snackbarHostState.showSnackbar(
                message = mensaje,
                duration = duration
            )
        }
    }
    // Obtenemos el estado actual de la lista de tareas
    val uiState by viewModel.listaTareasUiState.collectAsState()

    // Obtenemos el estado actual del dialogo de confirmación
    val dialogoConfirmacionUiState by viewModel.dialogoConfirmacionUiState.collectAsState()

    // Obtenemos el estado actual del filtro
    val filtroUiState by viewModel.filtroUiState.collectAsState()

    // Obtenemos el estado actual del switch
    val uiStateSinPagar by viewModel.uiStateSinPagar.collectAsState()

    // Obtenemos las categorías del contexto
    val categorias = LocalContext.current.resources.getStringArray(R.array.categorias).toList()


    if (dialogoConfirmacionUiState.mostrarDialogo) {
        DialogoDeConfirmacion(
            onDismissRequest = { viewModel.cancelarDialogo() },
            onConfirmation = { viewModel.aceptarDialogo() },
            dialogTitle = stringResource(R.string.confirmar_borrado),
            dialogText = stringResource(R.string.confirmar_borrado_texto),
            icon = Icons.Default.Delete
        )
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier,

        topBar = {
            topAppBarL(
                canGoBack = false,
                listaAcciones = ListaAccionesToolBar(muestraSnackBar),
                listaAccionesOverflow = ListaOverflowMenu(muestraSnackBar),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNuevaTareaClick // Abre la pantalla para crear una nueva tarea
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.nueva_tarea))
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .background(color = MaterialTheme.colorScheme.onPrimary)) {
            // Radio buttons para filtrar por estado
            basicRadioButton(
                radioOptions = viewModel.listaFiltrado,
                selectedOption = filtroUiState.filtroEstado,
                onOptionSelected = { viewModel.onCheckedChangeFiltroEstado(it) }
            )
            // Switch para mostrar tareas sin pagar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.sin_pagar),
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                )
                Switch(
                    checked = uiStateSinPagar.switchSinPagar,
                    onCheckedChange = { viewModel.onSwitchSinPagarChanged(it) },
                    modifier = Modifier.scale(0.8f) // Ajusta el tamaño del Switch
                )
            }

            // Verificamos si hay tareas para mostrar
            if (uiState.listaTareas.isNotEmpty()) {
                // Lista de tareas
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(uiState.listaTareas) { tarea ->
                        ItemCard(
                            tarea = tarea,
                            onClick = { onTareaClick(tarea.id!!) }, // Abre la pantalla de detalles de la tarea
                            onClickBorrar = { viewModel.onMostrarDialogoBorrar(tarea) }, // Muestra el dialogo de confirmación para borrar
                            listaCategorias = categorias
                        )

                    }
                }
            } else {
                // Texto para mostrar cuando la lista está vacía
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.no_hay_tareas))
                }
            }
        }
    }
}

/****
 * Crean las acciones de la toolbar
 *
 * puede que las acciones necesiten de los estados y estados  de la pantalla actual,
 * y sea necesario crear la lista en el mismo compose para crear las lambdas de las
 * acciones
 */
@Composable
private fun ListaAccionesToolBar(showSnackbar: (String, SnackbarDuration) -> Unit):List<ActionItem> {
    val textSearch = stringResource(R.string.buscar)
    val context = LocalContext.current
    val website = "https://portal.edu.gva.es/03013224/va/inici/"
    return listOf(
        ActionItem(
            textSearch,
            Icons.Filled.Search,
            action = { val intent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
                context.startActivity(intent) }
        )
    )
}

/**
 * Crean las acciones del overflow menu
 */
@Composable
private fun ListaOverflowMenu(showSnackbar: (String, SnackbarDuration) -> Unit):List<ActionItem> {
    val textCall = stringResource(R.string.llamar)
    val textAjustes = stringResource(R.string.ajustes)
    val context = LocalContext.current
    val phoneNumber = "966912260"
    return listOf(
        ActionItem(
            textCall,
            icon= Icons.Filled.Call,
            action = { val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                context.startActivity(intent) }
        ),
        ActionItem(
            textAjustes,
            icon=Icons.Default.Settings,
            action = { Toast.makeText(context, textAjustes, Toast.LENGTH_SHORT).show() }
        )
    )


}

/*
@Composable
fun TareaItem(tarea: Tarea, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = tarea.tecnico, style = MaterialTheme.typography.bodyLarge)
            Text(text = tarea.descripcion, style = MaterialTheme.typography.bodyMedium)
        }
    }
    }
 */

