package net.iesseveroochoa.victorsanchez.tareasv01.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.iesseveroochoa.victorsanchez.tareasv01.R


/**
 * Rating bar composable
 * currentRating - rating actual
 * onRatingChanged - callback
 * modifier - modificador
 */
@Composable
fun RatingBar(
    currentRating: Int,
    onRatingChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
    ){
        //Mostrar texto
        Text(
            text = stringResource(R.string.valoracion),
            modifier = Modifier.padding(5.dp)
        )
        //Mostrar las estrellas
        for (i in 1..5) {
            //Icono segun rating
            Icon(
                painter = when(i){
                    in 1..3-> painterResource(R.drawable.ic_happy_face)
                    in 4..5-> painterResource(R.drawable.ic_sad_face)
                    else->rememberVectorPainter(Icons.Filled.Clear)
                },
                contentDescription = "Star $i",
                modifier = modifier
                    .clickable { onRatingChanged(

                        if(i==1 && currentRating==1)
                            0
                        else
                            i) },
                //Color según rating
                tint = if (i >= (6-currentRating)) Color.Blue else Color.Gray // Los primeros hasta currentRating en azul, el resto en gris
            )
        }
    }
}