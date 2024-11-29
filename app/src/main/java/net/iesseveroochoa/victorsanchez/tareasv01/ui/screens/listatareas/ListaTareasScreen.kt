package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.listatareas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea
import androidx.lifecycle.viewmodel.compose.viewModel
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.DialogoDeConfirmacion
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.ItemCard
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.basicRadioButton

// Composable para mostrar la lista de tareas en la pantalla
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaTareasScreen(
    viewModel: ListaTareasViewModel = viewModel(),
    onNuevaTareaClick: () -> Unit,
    onTareaClick: (Long) -> Unit,
) {
    // Obtenemos el estado actual de la lista de tareas
    val uiState by viewModel.listaTareasUiState.collectAsState()

    // Obtenemos el estado actual del dialogo de confirmación
    val dialogoConfirmacionUiState by viewModel.dialogoConfirmacionUiState.collectAsState()

    // Obtenemos el estado actual del filtro
    val filtroUiState by viewModel.filtroUiState.collectAsState()

    // Obtenemos las categorías del contexto
    val categorias = LocalContext.current.resources.getStringArray(R.array.categorias).toList()
    val listaFiltrado = LocalContext.current.resources.getStringArray(R.array.filtro_estado).toList()

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
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.lista_de_tareas)) },
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

