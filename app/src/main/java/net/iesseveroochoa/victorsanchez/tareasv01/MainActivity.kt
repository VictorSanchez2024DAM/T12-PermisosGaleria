package net.iesseveroochoa.victorsanchez.tareasv01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import net.iesseveroochoa.victorsanchez.tareasv01.ui.components.TaskScreen
import net.iesseveroochoa.victorsanchez.tareasv01.ui.theme.TareasV01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                TareasV01Theme {
                    Surface() {
                        TaskScreen()}

                }

        }
    }
}

