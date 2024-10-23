package net.iesseveroochoa.victorsanchez.tareasv01.ui.components

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

/**
 * Función que muestra un radioButton
 */
@Composable
fun radioButton(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    radioOptions: List<String>,
    modifier: Modifier = Modifier
) {
    Row {
        // RadioButtons que recorre la lista de opciones
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .padding(horizontal = 16.dp)
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}