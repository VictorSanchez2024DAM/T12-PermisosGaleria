package net.iesseveroochoa.victorsanchez.tareasv01.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea

@Composable
fun ItemCard(
    tarea: Tarea,
    onClick: () -> Unit
) {
    // Estado para controlar si el elemento está expandido o colapsado
    var isExpanded by remember { mutableStateOf(false) }

    // Color de fondo basado en la prioridad
    val colorPrioridad = when (tarea.prioridad) {
        1 -> Color.Red // Prioridad alta
        else -> Color.Transparent
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = colorPrioridad)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Imagen del técnico o de la tarea
            Image(
                painter = painterResource(id = tarea.img.toInt()), // Suponiendo que existe un recurso de imagen
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                // Estado y categoría en una fila
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Icono de estado de la tarea
                    val estadoIcon = when (tarea.estado) {
                        0 -> R.drawable.ic_abierto   // "Abierto"
                        1 -> R.drawable.ic_encurso   // "En curso"
                        2 -> R.drawable.ic_cerrado   // "Cerrado"
                        else -> R.drawable.ic_abierto
                    }
                    Icon(
                        painter = painterResource(id = estadoIcon),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Categoría de la tarea
                    val categoriaText = when (tarea.categoria) {
                        0 -> "Reparación"
                        1 -> "Instalación"
                        2 -> "Mantenimiento"
                        3 -> "Comercial"
                        else -> "Otros"
                    }
                    Text(
                        text = categoriaText, // Categoría seleccionada
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Botón de expansión and Text composables within the Column
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { isExpanded = !isExpanded }
                    ) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = if (isExpanded) "Colapsar" else "Expandir"
                        )
                    }
                    // Nombre del técnico
                    Text(
                        text = tarea.tecnico,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Descripción, con máximo de dos líneas
                    Text(
                        text = tarea.descripcion,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                        overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

            }
        }
    }
}


