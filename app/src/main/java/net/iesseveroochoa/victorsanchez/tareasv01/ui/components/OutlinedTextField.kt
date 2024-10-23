package net.iesseveroochoa.victorsanchez.tareasv01.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

/**
 * Muestra un campo de texto.
 */
@Composable
fun MostrarOutlinedTextField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier
){
    // Campo de texto con label, valor, onValueChange y modificador
    OutlinedTextField(
        value = value,
        label =  { Text(text = stringResource(label)) },
        onValueChange = onValueChange,
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
    )
}