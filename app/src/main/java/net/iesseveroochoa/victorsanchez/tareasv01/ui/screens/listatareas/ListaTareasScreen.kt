package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.listatareas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea
import androidx.lifecycle.viewmodel.compose.viewModel
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.ItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaTareasScreen(
    viewModel: ListaTareasViewModel = viewModel(),
    onNuevaTareaClick: () -> Unit,
    onTareaClick: (Long) -> Unit,
) {
    // Obtenemos el estado actual de la lista de tareas
    val uiState by viewModel.listaTareasUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.lista_de_tareas)) }
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
        Column(modifier = Modifier.padding(innerPadding)) {
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
                            onClick = { onTareaClick(tarea.id!!) }
                        )
                        HorizontalDivider()
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
