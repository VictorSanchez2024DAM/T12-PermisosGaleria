package net.iesseveroochoa.victorsanchez.tareasv01.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import net.iesseveroochoa.victorsanchez.tareasv01.R
import net.iesseveroochoa.victorsanchez.tareasv01.data.db.entities.Tarea

@Composable
fun ItemCard(
    tarea: Tarea,
    onClick: () -> Unit,
    onClickBorrar: (Tarea) -> Unit = {},
    listaCategorias: List<String> = emptyList(),
    modifier: Modifier = Modifier
) {
    // Estado para controlar si el elemento está expandido o colapsado
    var isExpanded by remember { mutableStateOf(false) }

    // Color de fondo basado en la prioridad
    val colorPrioridad = when (tarea.prioridad) {
        0 -> MaterialTheme.colorScheme.tertiary // Prioridad alta
        else -> MaterialTheme.colorScheme.secondary
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
            AsyncImage(
                model = tarea.img.toInt(), // Suponiendo que existe un recurso de imagen
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)), // Esquinas redondeadas
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                        0 -> listaCategorias[0]
                        1 -> listaCategorias[1]
                        2 -> listaCategorias[2]
                        3 -> listaCategorias[3]
                        else -> listaCategorias[4]
                    }
                    Text(
                        text = categoriaText,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // ID de la tarea alineado a la derecha
                    Text(
                        text = "#${tarea.id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Nombre del técnico
                Text(
                    text = tarea.tecnico,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Descripción con control de expansión
                Text(
                    text = tarea.descripcion,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Botón para expandir/colapsar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { isExpanded = !isExpanded }) {
                        Text(
                            text = if (isExpanded) stringResource(R.string.colapsar) else stringResource(R.string.ver_m_s),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                IconButton(onClick = { onClickBorrar(tarea) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.borrar_tarea),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}


