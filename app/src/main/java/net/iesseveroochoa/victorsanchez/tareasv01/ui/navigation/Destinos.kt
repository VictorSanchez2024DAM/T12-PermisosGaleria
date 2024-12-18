package net.iesseveroochoa.victorsanchez.tareasv01.ui.navigation



const val ListaTareasDestination = "lista_tareas"
const val TareaDestination = "tarea/{tareaId}"
const val FotoDestination = "foto/{fotoUri}"

fun getTareaDestination(tareaId: Long? = null) = "tarea/${tareaId ?: "null"}"
fun getFotoDestination(fotoUri: String? = null) = "foto/${fotoUri ?: "null"}"

