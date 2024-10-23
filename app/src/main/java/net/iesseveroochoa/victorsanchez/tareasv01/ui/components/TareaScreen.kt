package net.iesseveroochoa.victorsanchez.tareasv01.ui.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.ui.theme.ColorPrioridadAlta

/**
 * Función que muestra la interfaz de la aplicación tarea.
 */
@Composable
fun TaskScreen(modifier: Modifier = Modifier){
    val categorias = stringArrayResource(id = R.array.categorias).toList()
    var categoriaActual by remember { mutableStateOf(categorias[0]) }
    val prioridades = stringArrayResource(id = R.array.prioridad).toList()
    var prioridadActual by remember { mutableStateOf(prioridades[2]) }
    var isChecked by remember { mutableStateOf(false) }
    val radioOptions = stringArrayResource(R.array.estado).toList()
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    var currentRating by remember { mutableIntStateOf(0) }
    var tecnicoValue by remember { mutableStateOf("") }
    var descripcionValue by remember { mutableStateOf("") }
    val PRIORIDAD_ALTA = prioridades[0]
    val colorFondo = if (PRIORIDAD_ALTA==prioridadActual) ColorPrioridadAlta else Color.Transparent
    Column (
        modifier = modifier
            .background(color = colorFondo)
            .padding(top = 32.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
            .fillMaxSize()
    ){
        Row (
            verticalAlignment = CenterVertically,
        ){
            Column (
                modifier = modifier.weight(1f),
            ){
                //Mostrar el campo de texto para la categoría
                DynamicSelectTextField(
                    selectedValue = categoriaActual,
                    options = categorias,
                    label = stringResource(R.string.categor_a),
                    onValueChangedEvent = {
                        categoriaActual = it
                    },
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                //Mostrar el campo de texto para la prioridad
                DynamicSelectTextField(
                    selectedValue = prioridadActual,
                    options = prioridades,
                    label = stringResource(R.string.prioridad),
                    onValueChangedEvent = {
                        prioridadActual = it
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
            if (isChecked)
                Icon(painterResource(R.drawable.ic_pagado), contentDescription = null, modifier = Modifier.padding(8.dp))
            else
                Icon(painterResource(R.drawable.ic_no_pagado), contentDescription = null, modifier = Modifier.padding(8.dp))
            Text(
                text = stringResource(R.string.est_pagado),
                modifier
                    .padding(5.dp)
            )
            Switch(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
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
            when (selectedOption) {
            radioOptions[0] -> Icon(painterResource(R.drawable.ic_abierto), contentDescription = null, modifier = Modifier.padding(8.dp))
                radioOptions[1] -> Icon(painterResource(R.drawable.ic_encurso), contentDescription = null, modifier = Modifier.padding(8.dp))
                radioOptions[2] -> Icon(painterResource(R.drawable.ic_cerrado), contentDescription = null, modifier = Modifier.padding(8.dp))
            }
        }
        //Mostrar el radio button con las opciones de estado
        radioButton(selectedOption, onOptionSelected, radioOptions)
        //Mostrar el rating bar con la valoracion
        RatingBar(
            currentRating = currentRating,
            onRatingChanged = { currentRating = when(it){
                1->5
                2->4
                3->3
                4->2
                5->1
                else -> 0
            } }
        )
        //Mostrar el campo de texto para el tecnico y la descripcion con scroll vertical
        MostrarOutlinedTextField(
            label = R.string.tecnico,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            value = tecnicoValue,
            onValueChange = { tecnicoValue = it },
            modifier = Modifier
                .fillMaxWidth()
        )
        //Scroll vertical para la descripcion
        Box(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ){
            //Mostrar el campo de texto para la descripcion
            MostrarOutlinedTextField(
                label = R.string.descripcion,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                value = descripcionValue,
                onValueChange = { descripcionValue = it },
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    TaskScreen()
}