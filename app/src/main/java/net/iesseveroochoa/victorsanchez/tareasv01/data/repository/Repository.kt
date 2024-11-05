package net.iesseveroochoa.victorsanchez.tareasv01.data.repository

import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea
import net.iesseveroochoa.victorsanchez.tareasv01.data.tempmodel.TempModelTareas

object Repository {
    //inicio del objeto singleton
    operator fun invoke() {
    //iniciamos el modelo
        TempModelTareas()
    }
    fun addTarea(tarea: Tarea)= TempModelTareas.addTarea(tarea)
    fun delTarea(tarea: Tarea)= TempModelTareas.delTarea(tarea)
    fun getAllTareas()= TempModelTareas.getAllTareas()
    fun getTarea(id:Long)= TempModelTareas.getTarea(id)

}