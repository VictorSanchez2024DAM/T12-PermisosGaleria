package net.iesseveroochoa.victorsanchez.tareasv01

import android.app.Application
import net.iesseveroochoa.victorsanchez.tareasv01.data.repository.Repository

class TareasApplication: Application() {
    /**
     * Inicializa el repositorio y guardamos el contexto en un companion object
     */
    companion object{
        lateinit var application: TareasApplication
    }
    override fun onCreate() {
        super.onCreate()
        application = this
        //iniciamos el Repository
        Repository()

    }
}