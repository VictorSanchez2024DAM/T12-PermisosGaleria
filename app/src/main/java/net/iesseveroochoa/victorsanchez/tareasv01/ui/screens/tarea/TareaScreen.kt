package net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.tarea

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.DialogoDeConfirmacion
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.basicRadioButton
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.dynamicSelectTextField
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.ratingBar
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.showOutlinedTextField
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.topAppBarT
import net.iesseveroochoa.victorsanchez.tareasv01.utils.loadFromUri
import net.iesseveroochoa.victorsanchez.tareasv01.utils.saveBitmapImage


/**
 * Función que muestra la interfaz de la aplicación tarea.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun taskScreen(
    viewModel: TareaViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    tareaId: Long? = null,
    onFotoClick: (String) -> Unit
) {
    // Cargar la tarea si el id no es nulo
    LaunchedEffect(tareaId) {
        if (tareaId != null) {
            viewModel.getTarea(tareaId)
        }
    }

    val uiStateTarea by viewModel.uiStateTarea.collectAsState()
    val message = stringResource(R.string.rellene_todos_los_campos)

    // Creamos el scope para las corrutinas
    val scope = rememberCoroutineScope()

    // Creamos el context local
    val context = LocalContext.current

    /*
    Permisos:
    Petición de permisos múltiples condicionales según la versión de Android
    */
    val permissionState = rememberMultiplePermissionsState(
        permissions = mutableListOf(
            //permiso para hacer fotos
            android.Manifest.permission.CAMERA
        ).apply {//Permisos para la galería
            //Si es Android menor de 10
            if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.P) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }//si es Android igual o superior a 13
            if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.TIRAMISU
            ) {
                add(android.Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
    )

    /*
        Llamada a Galeria versión por encima de Versión 13 en Android. Para usarlo en
        versiones inferiores
        tenéis incluir el Service de google que aparece en el manifest.xml
    */
    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            //hacemos una copia de foto, ya que en las nuevas versiones solo nos deja acceso en esta sesión
            //lanzamos una corrutina para que no se bloquee el hilo principal
                    uri?.let {//si no es null
                        scope.launch {
                            val uriCopia = saveBitmapImage(context, loadFromUri(context, uri)!!)
                            viewModel.setUri(uriCopia.toString())
                        }
                    }
        }
    )


    // Muestra la interfaz de la aplicación con un Scaffold
    Scaffold(
        topBar = {
            // Llamamos a CustomTopAppBar y le pasamos el viewModel y la función de retroceso
            topAppBarT(
                viewModel = viewModel,
                onBackClick = onNavigateBack,
                tareaId = tareaId
            )
        },
        modifier = Modifier.fillMaxSize(),
        // Configura el SnackbarHost
        snackbarHost = { SnackbarHost(uiStateTarea.snackbarHostState) },
        // Configura el botón flotante
        floatingActionButton = {
            FloatingActionButton(

                onClick = { // Cuando se hace clic en el botón flotante se llama a la función onGuardar
                    if (uiStateTarea.esFormularioValido) {
                        viewModel.onGuardar()
                        onNavigateBack()
                    } else {
                        uiStateTarea.scope.launch {
                            uiStateTarea.snackbarHostState.showSnackbar(
                                message = message,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            ) {
                // Muestra un ícono de guardar
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_save),  // Ícono de guardar
                    contentDescription = stringResource(R.string.guardar)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .background(color = uiStateTarea.colorFondo)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = CenterVertically,
            ) {
                Column(
                    modifier = modifier.weight(1f),
                ) {
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
                AsyncImage(
                    model = if(uiStateTarea.uriImagen.isEmpty())
                        R.drawable.no_image
                    else
                        uiStateTarea.uriImagen,
                    contentDescription = null,
                    modifier = modifier
                        .size(100.dp)
                        .padding(start = 5.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .weight(1f)
                        .clickable { onFotoClick(uiStateTarea.uriImagen) }

                )


            }
            //Mostrar el Switch para el pago
            Row(
                verticalAlignment = CenterVertically,
            ) {
                //Icono de pagado o no pagado segun el estado actual
                if (uiStateTarea.checked)
                    Icon(
                        painterResource(R.drawable.ic_pagado),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                else
                    Icon(
                        painterResource(R.drawable.ic_no_pagado),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                Text(
                    text = stringResource(R.string.est_pagado),
                    modifier
                        .padding(5.dp)
                )
                Switch(
                    checked = uiStateTarea.checked,
                    onCheckedChange = { viewModel.onCheckedChange(it) },
                    modifier = Modifier.padding(5.dp)
                )

                Spacer(modifier = Modifier.width(50.dp))
                IconButton(
                    onClick = { if(!permissionState.allPermissionsGranted)
                        permissionState.launchMultiplePermissionRequest()
                            else
                                launcherGaleria.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = stringResource(R.string.buscar_foto),
                        modifier = Modifier
                            .size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))
                IconButton(
                    onClick = { if(!permissionState.allPermissionsGranted)
                        permissionState.launchMultiplePermissionRequest()}
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_camera),
                        contentDescription = stringResource(R.string.hacer_foto),
                        modifier = Modifier
                            .size(24.dp)
                    )
                }

            }


            Row(
                verticalAlignment = CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.estado_de_la_tarea),
                    modifier = Modifier.padding(5.dp)
                )
                //Icono de la tarea segun su estado actual
                when (uiStateTarea.estado) {
                    viewModel.radioOptions[0] -> Icon(
                        painterResource(R.drawable.ic_abierto),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )

                    viewModel.radioOptions[1] -> Icon(
                        painterResource(R.drawable.ic_encurso),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )

                    viewModel.radioOptions[2] -> Icon(
                        painterResource(R.drawable.ic_cerrado),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            //Mostrar el radio button con las opciones de estado
            basicRadioButton(
                uiStateTarea.estado,
                viewModel::onValueChangeEstado,
                viewModel.radioOptions
            )


            //Mostrar el rating bar con la valoracion
            ratingBar(
                currentRating = uiStateTarea.valoracion,
                onRatingChanged = {
                    viewModel.onRatingChanged(it)
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
            ) {
                //Mostrar el campo de texto para la descripcion
                showOutlinedTextField(
                    label = R.string.descripcion,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
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
                        uiStateTarea.scope.launch {
                            uiStateTarea.snackbarHostState.showSnackbar(
                                message = "Tarea guardada",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    dialogTitle = stringResource(R.string.atenci_n),
                    dialogText = stringResource(R.string.desea_guardar_los_cambios),
                    icon = Icons.Default.Info
                )
            }
        }
    }
}


