package net.iesseveroochoa.victorsanchez.tareasv01.ui.navigation



const val ListaTareasDestination = "lista_tareas"
const val TareaDestination = "tarea/{tareaId}"

fun getTareaDestination(tareaId: Long? = null) = "tarea/${tareaId ?: "null"}"

