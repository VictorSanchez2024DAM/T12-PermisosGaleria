package net.iesseveroochoa.victorsanchez.tareasv01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.tarea.taskScreen
import net.iesseveroochoa.victorsanchez.tareasv01.ui.theme.TareasV01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                TareasV01Theme {
                        taskScreen()
                }

        }
    }
}

