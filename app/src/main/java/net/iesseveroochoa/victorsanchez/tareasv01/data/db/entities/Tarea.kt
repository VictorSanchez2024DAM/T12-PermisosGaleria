package net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities

data class Tarea(
    var id:Long?=null,//id único
    val categoria:Int,
    val prioridad:Int,
    val img:String,
    val pagado:Boolean,
    val estado:Int,
    val valoracionCliente:Int,
    val tecnico:String,
    val descripcion:String
) {
    companion object {
        var idContador = 1L//iniciamos contador de tareas
        private fun generateId(): Long {
            return idContador++//sumamos uno al contador
        }
    }

    //dos tareas son iguales cuando su id es igual.
// Facilita la búsqueda en un arrayList
    override fun equals(other: Any?): Boolean {
        return (other is Tarea)&&(this.id == other?.id)
    }

    //segundo constructor que genera id nuevo
    constructor( categoria:Int,
                 prioridad:Int,
                 img:String,
                 pagado:Boolean,
                 estado:Int,
                 valoracionCliente:Int,
                 tecnico:String,
                 descripcion:String):this(generateId(),categoria,prioridad,img,pagado,estado,valoracionCliente, tecnico, descripcion){}
}
