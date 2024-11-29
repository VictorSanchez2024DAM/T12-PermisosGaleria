package net.iesseveroochoa.victorsanchez.tareasv01.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea

/**
 * Interfaz de acceso a datos para la entidad Tarea
 */
@Dao
interface TareasDao {

    // Inserta una tarea en la base de datos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTarea(tarea: Tarea)

    // Borra una tarea de la base de datos
    @Delete
    suspend fun delTarea(tarea: Tarea)

    // Obtiene todas las tareas de la base de datos
    @Query("SELECT * FROM tareas")
    fun getTareas(): Flow<List<Tarea>>

    // Obtiene una tarea por su ID
    @Query("SELECT * FROM tareas WHERE id = :id")
    suspend fun getTarea(id: Long): Tarea

    // Obtiene las tareas por estado
    @Query("SELECT * FROM tareas WHERE estado = :estado")
    fun getTareasPorEstado(estado: Int): Flow<List<Tarea>>

    // Obtiene las tareas sin pagar
    @Query("SELECT * FROM tareas WHERE pagado = 0")
    fun getTareasSinPagar(): Flow<List<Tarea>>

    // Obtiene las tareas sin pagar por estado
    @Query("SELECT * FROM tareas WHERE estado = :estado AND pagado = 0")
    fun getTareasPorEstadoYNoPagado(estado: Int): Flow<List<Tarea>>

}