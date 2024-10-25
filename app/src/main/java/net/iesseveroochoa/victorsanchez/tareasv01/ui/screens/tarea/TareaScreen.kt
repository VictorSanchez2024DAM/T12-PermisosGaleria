package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.tarea

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.DialogoDeConfirmacion
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.basicRadioButton
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.dynamicSelectTextField
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.ratingBar
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.showOutlinedTextField


/**
 * Función que muestra la interfaz de la aplicación tarea.
 */
@Composable
fun taskScreen(viewModel: TareaViewModel = viewModel(),
               modifier: Modifier = Modifier){

    val uiStateTarea by viewModel.uiStateTarea.collectAsState()

    // val categorias = stringArrayResource(id = R.array.categorias).toList()
    // var categoriaActual by remember { mutableStateOf(categorias[0]) }
    // val prioridades = stringArrayResource(id = R.array.prioridad).toList()
    // var prioridadActual by remember { mutableStateOf(prioridades[2]) }
    // var isChecked by remember { mutableStateOf(false) }
    // val radioOptions = stringArrayResource(R.array.estado).toList()
    // val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    // var currentRating by remember { mutableIntStateOf(0) }
    // var tecnicoValue by remember { mutableStateOf("") }
    // var descripcionValue by remember { mutableStateOf("") }
    // val PRIORIDAD_ALTA = prioridades[0]
    // val colorFondo = if (PRIORIDAD_ALTA==prioridadActual) ColorPrioridadAlta else Color.Transparent
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },  // Asegura que el SnackbarHost esté bien configurado
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (uiStateTarea.esFormularioValido) {
                        viewModel.onGuardar()
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Rellene todos los campos",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            ) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_save),  // Ícono de guardar
                    contentDescription = "guardar"
                )
            }
        }
    ) { innerPadding  ->
    Column (
        modifier = modifier
            .background(color = uiStateTarea.colorFondo)
            .padding(innerPadding)
            .fillMaxSize()
    ){
        Row (
            verticalAlignment = CenterVertically,
        ){
            Column (
                modifier = modifier.weight(1f),
            ){
                //Mostrar el campo de texto para la categoría
                dynamicSelectTextField(
                    selectedValue = uiStateTarea.categoria,
                    options = viewModel.categorias,
                    label = stringResource(R.string.categor_a),
                    onValueChangedEvent = {
                        viewModel.onValueChangeCategoria(it)
                    },
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                //Mostrar el campo de texto para la prioridad
                dynamicSelectTextField(
                    selectedValue = uiStateTarea.prioridad,
                    options = viewModel.listaPrioridad,
                    label = stringResource(R.string.prioridad),
                    onValueChangedEvent = {
                        viewModel.onValueChangePrioridad(it)
                    }
                )
            }
            //Mostrar la imagen de la tarea
            Image(
                painter = painterResource(R.drawable.tarea),
                contentDescription = null,
                modifier = modifier
                    .size(100.dp)
                    .padding(start = 5.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .weight(1f)
            )
        }
        //Mostrar el Switch para el pago
        Row(
            verticalAlignment = CenterVertically,
        ) {
            //Icono de pagado o no pagado segun el estado actual
            if (uiStateTarea.checked)
                Icon(painterResource(R.drawable.ic_pagado), contentDescription = null, modifier = Modifier.padding(8.dp))
            else
                Icon(painterResource(R.drawable.ic_no_pagado), contentDescription = null, modifier = Modifier.padding(8.dp))
            Text(
                text = stringResource(R.string.est_pagado),
                modifier
                    .padding(5.dp)
            )
            Switch(
                checked = uiStateTarea.checked,
                onCheckedChange = { viewModel.onCheckedChange(it)},
                modifier = Modifier.padding(5.dp)
            )
        }
        Row (
            verticalAlignment = CenterVertically,
        ){
            Text(
                text = stringResource(R.string.estado_de_la_tarea),
                modifier = Modifier.padding(5.dp)
            )
            //Icono de la tarea segun su estado actual
            when (uiStateTarea.estado) {
            viewModel.radioOptions[0] -> Icon(painterResource(R.drawable.ic_abierto), contentDescription = null, modifier = Modifier.padding(8.dp))
                viewModel.radioOptions[1] -> Icon(painterResource(R.drawable.ic_encurso), contentDescription = null, modifier = Modifier.padding(8.dp))
                viewModel.radioOptions[2] -> Icon(painterResource(R.drawable.ic_cerrado), contentDescription = null, modifier = Modifier.padding(8.dp))
            }
        }
        //Mostrar el radio button con las opciones de estado
        basicRadioButton(uiStateTarea.estado, viewModel::onValueChangeEstado, viewModel.radioOptions)


        //Mostrar el rating bar con la valoracion
        ratingBar(
            currentRating = uiStateTarea.valoracion,
            onRatingChanged = { val nuevaValoracion = when(it)
            {
                1->5
                2->4
                3->3
                4->2
                5->1
                else -> 0
            }
                viewModel.onRatingChanged(nuevaValoracion)
            }
        )
        //Mostrar el campo de texto para el tecnico y la descripcion con scroll vertical
        showOutlinedTextField(
            label = R.string.tecnico,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            value = uiStateTarea.tecnico,
            onValueChange = { viewModel.onValueChangeTecnico(it) },
            modifier = Modifier
                .fillMaxWidth()
        )
        //Scroll vertical para la descripcion
        Box(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ){
            //Mostrar el campo de texto para la descripcion
            showOutlinedTextField(
                label = R.string.descripcion,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                value = uiStateTarea.descripcion,
                onValueChange = { viewModel.onValueChangeDescripcion(it) },
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        //Dialogo de confirmación
        if (uiStateTarea.mostrarDialogo) {
            DialogoDeConfirmacion(
                onDismissRequest = {
                    //cancela el dialogo
                    viewModel.onCancelarDialogoGuardar()
                },
                onConfirmation = {
                    //guardaría los cambios
                    viewModel.onConfirmarDialogoGuardar()
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Tarea guardada",
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                dialogTitle = "Atención",
                dialogText = "Desea guardar los cambios?",
                icon = Icons.Default.Info
            )
        }
    }
    }
}

// Vista previa de la funcion TaskScreen
@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    taskScreen()
}