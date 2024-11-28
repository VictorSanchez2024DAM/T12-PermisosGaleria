package net.iesseveroochoa.victorsanchez.tareasv01.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.data.db.dao.TareasDao
import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea
import kotlin.random.Random

@Database(entities = arrayOf(Tarea::class), version = 1, exportSchema
= false)
public abstract class TareasDataBase : RoomDatabase() {
    abstract fun tareasDao(): TareasDao
    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: TareasDataBase? = null
        fun getDatabase(context: Context): TareasDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TareasDataBase::class.java,
                    "tareas_database"
                )
                    .addCallback(InicioDbCallback())
                    .build()
                INSTANCE = instance
            // return instance
                instance
            }
        }
    }
    //***************CallBack******************************
    /**
     * Permite iniciar la base de datos con Tareas
     */
    private class InicioDbCallback() : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                GlobalScope.launch {
                    cargarDatabase(database.tareasDao())
                }
            }
        }
        //Iniciamos la base de datos con Tareas de ejemplo
        suspend fun cargarDatabase(tareasDao: TareasDao) {
            val tecnicos = listOf(
                "Pepe Gotero",
                "Sacarino Pómez",
                "Mortadelo Fernández",
                "Filemón López",
                "Zipi Climent",
                "Zape Gómez"
            )
            val fotos =
                listOf(
                    R.drawable.foto1, R.drawable.foto2, R.drawable.foto3,
                    R.drawable.foto4)
            lateinit var tarea: Tarea
            (1..10).forEach {
                tarea = Tarea(
                    (0..4).random(),
                    (0..2).random(),
                    fotos.random().toString(),
                    Random.nextBoolean(),
                    (0..2).random(),
                    (0..5).random(),
                    tecnicos.random(),
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris consequat ligula et vehicula mattis. \n Etiam tristique ornare lacinia. \nVestibulum lacus magna, dignissim et tempor id, convallis sed augue"
                )
                tareasDao.addTarea(tarea)
            }
        }
    }
}